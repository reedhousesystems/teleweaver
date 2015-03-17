package com.reedhousesystems.services.core.health.messenger.model;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.Sort;
import org.hibernate.annotations.SortType;
import org.hibernate.validator.constraints.Length;
import org.springframework.util.StringUtils;

import com.reedhousesystems.services.core.health.messenger.util.HashUtil;

@Entity
@Table(name="USER_TBL")
public class User extends BaseEntity {

    /**
    * Add additional salt to password hashing
    */
   private static final String HASH_SALT = "d8a8e885-ecce-42bb-8332-894f20f0d8ed";

   private static final int HASH_ITERATIONS = 1000;
	
   @NotNull
   @Column(name="first_name")
   private String firstName;
	
   @NotNull
   @Column(name="last_name")
   private String lastName;
	
   @NotNull
   @Column(name="user_name")
   private String userName;
	
   @Column
   @NotNull
   @Length(min=8)
   private String password;
	
   @Column(name="login_attempts")
   private int loginAttempts;
	
   @Column
   private String salt;
	
   @Column
   private int status;					//0 = logged out, 1 = logged in, 2 = locked, 3 = inactive

   private boolean isVerified;
    
   @OneToOne
   @JoinColumn(name="demographic_id")
   private Demographic demographics;
	
   @Embedded
   private Contact contact;

   @Enumerated(EnumType.STRING)
   private Role role;

   @OneToMany(mappedBy="user",
                 targetEntity=VerificationToken.class,
                 cascade= CascadeType.ALL)
   @LazyCollection(LazyCollectionOption.FALSE)
   private List<VerificationToken> verificationTokens = new ArrayList<VerificationToken>();

   @OneToMany(mappedBy="user",
                 targetEntity=SessionToken.class,
                 cascade= CascadeType.ALL, orphanRemoval = true)
   @LazyCollection(LazyCollectionOption.FALSE)
   @Sort(type = SortType.NATURAL)
   private SortedSet<SessionToken> sessions = Collections.synchronizedSortedSet(new TreeSet<SessionToken>(Collections.<SessionToken>reverseOrder()));
	
   @ManyToMany(mappedBy = "users")
   private List<Event> events = new ArrayList<Event>();

   public User() {
	   this(UUID.randomUUID());
   }

   public User(UUID uuid) {
       super(uuid);
       setRole(Role.anonymous); //all users are anonymous until credentials are proved
   }

   public User(ExternalUser externalUser) {
       this(UUID.fromString(externalUser.getId()));
       this.firstName = externalUser.getFirstName();
       this.lastName = externalUser.getLastName();
   }

   public String getFirstName() {
	   return firstName;
   }

   public void setFirstName(String firstName) {
	   this.firstName = firstName;
   }

   public String getLastName() {
	   return lastName;
   }

   public void setLastName(String lastName) {
	   this.lastName = lastName;
   }

   public String getUserName() {
	   return userName;
   }
	
   public void setUserName(String userName) {
	   this.userName = userName;
   }
	
   public String getPassword() {
	   return password;
   }
	
   public void setPassword(String password) {
	   this.password = password;
   }
	
   public int getLoginAttempts() {
	   return loginAttempts;
   }
	
   public void setLoginAttempts(int loginAttempts) {
	   this.loginAttempts = loginAttempts;
   }
	
   public String getSalt() {
	   return salt;
   }
	
   public void setSalt(String salt) {
	   this.salt = salt;
   }
	
   public int getStatus() {
	   return status;
   }
	
   public void setStatus(int status) {
	   this.status = status;
   }
	
   public boolean isVerified() {
	   return isVerified;
   }
	
   public void setVerified(boolean isVerified) {
	   this.isVerified = isVerified;
   }

   public Demographic getDemographics() {
	   return demographics;
   }

   public void setDemographics(Demographic demographics) {
	   this.demographics = demographics;
   }

   public Contact getContact() {
	   return contact;
   }

   public void setContact(Contact contact) {
	   this.contact = contact;
   }

   public Role getRole() {
	   return role;
   }

   public void setRole(Role role) {
	   this.role = role;
   }

   public boolean hasRole(Role role) {
       return role.equals(this.role);
   }
   
   public boolean equals(Object otherUser) {
       boolean response = false;

       if(otherUser == null) {
           response = false;
       }
       else if(! (otherUser instanceof User)) {
           response = false;
       }
       else {
           if(((User)otherUser).getUuid().equals(this.getUuid())) {
               response = true;
           }
       }

       return response;
   }

   public int hashCode() {
       return getUuid().hashCode();
   }

   public String getName() {
       if(StringUtils.hasText(getFirstName())) {
          return getFirstName() + " " + getLastName();
       }
       return "";
   }

   public synchronized void addVerificationToken(VerificationToken token) {
       verificationTokens.add(token);
   }

   public synchronized List<VerificationToken> getVerificationTokens() {
       return Collections.unmodifiableList(this.verificationTokens);
   }

   public SessionToken addSessionToken() {
       SessionToken token = new SessionToken(this);
       this.sessions.add(token);
       return token;
   }

   public SortedSet<SessionToken> getSessions() {
       SortedSet copySet =  new TreeSet<SessionToken>(Collections.<SessionToken>reverseOrder());
       copySet.addAll(this.sessions);
       return Collections.unmodifiableSortedSet(copySet);
   }

   public void removeSession(SessionToken session) {
       this.sessions.remove(session);
   }

   /**
     * If the user has a VerificationToken of type VerificationTokenType.lostPassword
     * that is active return it otherwise return null
     *
     * @return verificationToken
     */
   public VerificationToken getActiveLostPasswordToken() {
       return getActiveToken(VerificationToken.VerificationTokenType.lostPassword);
   }

   /**
     * If the user has a VerificationToken of type VerificationTokenType.emailVerification
     * that is active return it otherwise return null
     *
     * @return verificationToken
     */
   public VerificationToken getActiveEmailVerificationToken() {
       return getActiveToken(VerificationToken.VerificationTokenType.emailVerification);
   }

   /**
     * If the user has a VerificationToken of type VerificationTokenType.emailRegistration
     * that is active return it otherwise return null
     *
     * @return verificationToken
     */
   public VerificationToken getActiveEmailRegistrationToken() {
       return getActiveToken(VerificationToken.VerificationTokenType.emailRegistration);
   }

   private VerificationToken getActiveToken(VerificationToken.VerificationTokenType tokenType) {
        VerificationToken activeToken = null;
       for (VerificationToken token : getVerificationTokens()) {
           if (token.getTokenType().equals(tokenType)
                   && !token.hasExpired() && !token.isVerified()) {
               activeToken = token;
               break;
           }
       }
       return activeToken;
   }

   public void setActiveSession(UserSession session) {
       for(SessionToken token : getSessions()) {
            if(token.getToken().equals(session.getSessionToken())) {
                token.setLastUpdated(new Date());
            }
       }
   }

   public void removeExpiredSessions(Date expiryDate) {
       for(SessionToken token : getSessions()) {
           if(token.getLastUpdated().before(expiryDate)) {
                removeSession(token);
           }
       }
   }

   /**
     * Hash the password using salt values
     * See https://www.owasp.org/index.php/Hashing_Java
     *
     * @param passwordToHash
     * @return hashed password
     */
   public String hashPassword(String passwordToHash) throws Exception {
       return hashToken(passwordToHash, getUuid().toString() + HASH_SALT );
   }


   private String hashToken(String token, String salt) throws Exception {
       return HashUtil.byteToBase64(getHash(HASH_ITERATIONS, token, salt.getBytes()));
   }

   public byte[] getHash(int numberOfIterations, String password, byte[] salt) throws Exception {
      MessageDigest digest = MessageDigest.getInstance("SHA-256");
      digest.reset();
      digest.update(salt);
      byte[] input = digest.digest(password.getBytes("UTF-8"));
      for (int i = 0; i < numberOfIterations; i++) {
          digest.reset();
          input = digest.digest(input);
      }
      return input;
   }

	public List<Event> getEvents() {
		return events;
	}
	
	public void setEvents(List<Event> events) {
		this.events = events;
	}
	
}