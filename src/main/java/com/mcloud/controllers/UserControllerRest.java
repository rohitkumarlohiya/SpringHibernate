/*
 * package com.mcloud.controllers; import java.util.ArrayList; import
 * java.util.HashSet; import java.util.List; import java.util.Set;
 * 
 * import org.apache.log4j.Logger; import org.hibernate.HibernateException;
 * import org.springframework.beans.factory.annotation.Autowired; import
 * org.springframework.beans.factory.annotation.Qualifier; import
 * org.springframework.context.annotation.PropertySource; import
 * org.springframework.core.env.Environment; import
 * org.springframework.http.HttpStatus; import
 * org.springframework.http.MediaType; import
 * org.springframework.security.core.context.SecurityContextHolder; import
 * org.springframework.security.core.userdetails.UserDetails; import
 * org.springframework.stereotype.Controller; import
 * org.springframework.web.bind.annotation.PathVariable; import
 * org.springframework.web.bind.annotation.RequestBody; import
 * org.springframework.web.bind.annotation.RequestMapping; import
 * org.springframework.web.bind.annotation.RequestMethod; import
 * org.springframework.web.bind.annotation.RequestParam; import
 * org.springframework.web.bind.annotation.ResponseBody;
 * 
 * import com.mcloud.constants.ApplicationConstants; import
 * com.mcloud.domains.custom.CustomAccounts; import
 * com.mcloud.domains.custom.CustomProfile; import
 * com.mcloud.domains.custom.CustomProfileForNodeApp; import
 * com.mcloud.entities.AbstractEntity; import com.mcloud.entities.CustomUser;
 * import com.mcloud.exceptions.custom.EmptyFormElementsException; import
 * com.mcloud.exceptions.custom.EntityNotFoundException; import
 * com.mcloud.exceptions.custom.SomethingWentWrongException; import
 * com.mcloud.formvalidation.rest.EditProfileFormValidation; import
 * com.mcloud.formvalidation.rest.SignupFormValidation; import
 * com.mcloud.models.commons.Errors; import com.mcloud.models.commons.Status;
 * import com.mcloud.models.enrollements.CoursesEnrollements; import
 * com.mcloud.models.login.Accounts; import com.mcloud.models.login.UserRole;
 * import com.mcloud.models.profiles.Profiles; import
 * com.mcloud.services.CustomServices; import com.mcloud.services.DataServices;
 * import com.mcloud.services.MailBodyTempletes; import
 * com.mcloud.services.MailService; import com.mcloud.utils.StringToHex; import
 * com.mcloud.utils.Utils;
 * 
 * @Controller
 * 
 * @RequestMapping("api/users")
 * 
 * @PropertySource("classpath:message.properties") public class
 * UserControllerRest {
 * 
 * @Autowired Utils utils;
 * 
 * @Autowired Environment env;
 * 
 * @Autowired
 * 
 * @Qualifier("mailService") MailService mailService;
 * 
 * @Autowired DataServices<AbstractEntity> dataServices;
 * 
 * @Autowired CustomServices customService;
 * 
 * @Autowired MailBodyTempletes mailBodyTempletes;
 * 
 * @Autowired SignupFormValidation signupFormValidation;
 * 
 * @Autowired EditProfileFormValidation editProfileFormValidation;
 * 
 * @Autowired LoginServices loginServices;
 * 
 * static final Logger logger = Logger.getLogger(UserControllerRest.class);
 * 
 * 
 * Get User Login Details
 * 
 * @RequestMapping(value = "/", method = RequestMethod.GET)
 * 
 * @ResponseBody public CustomAccounts getUser() throws HibernateException,
 * Exception { Accounts accounts ; CustomAccounts customAccounts ;
 * 
 * UserDetails userDetails = (UserDetails) SecurityContextHolder
 * .getContext().getAuthentication().getPrincipal();
 * 
 * String username = userDetails.getUsername();
 * 
 * logger.info("Username: " + username); accounts =
 * dataServices.findByUserName(username);
 * 
 * if (accounts == null) { throw new EntityNotFoundException(); }
 * 
 * Set<String> roles = new HashSet<>(); for (UserRole role :
 * accounts.getUserRole()) { roles.add(role.getRole()); }
 * 
 * customAccounts = new CustomAccounts(accounts.getAccountEmail(),
 * accounts.getAccountPhone(), accounts.getPassword(), accounts.getFirstName(),
 * accounts.getLastName(), accounts.getAccessLevel(), accounts.isActivated(),
 * roles);
 * 
 * return customAccounts; }
 * 
 * 
 * Update User
 * 
 * @RequestMapping(value = "/", method = RequestMethod.PUT, consumes =
 * MediaType.APPLICATION_JSON_VALUE)
 * 
 * @ResponseBody public Status editUser(@RequestBody Accounts accounts) throws
 * HibernateException, Exception { if (accounts == null) { throw new
 * EmptyFormElementsException(); }
 * 
 * // need a little optimization List<Errors> errorsList =
 * signupFormValidation.validateForm(accounts); if (!errorsList.isEmpty()) {
 * return new Status(HttpStatus.NOT_ACCEPTABLE.toString(),
 * env.getProperty("exception.validationfailed"), errorsList);
 * 
 * } dataServices.updateUserEntity(accounts);
 * 
 * return new Status(HttpStatus.OK.toString(),
 * env.getProperty("status.successupdate")); }
 * 
 * 
 * Get User Profile
 * 
 * @RequestMapping(value = "/profile", method = RequestMethod.GET)
 * 
 * @ResponseBody public CustomProfile getUserProfile() throws
 * HibernateException, Exception { Accounts accounts; Profiles profiles;
 * CustomProfile customProfile;
 * 
 * UserDetails userDetails = (UserDetails) SecurityContextHolder
 * .getContext().getAuthentication().getPrincipal(); String username =
 * userDetails.getUsername(); accounts = dataServices.findByUserName(username);
 * if (accounts == null) throw new EntityNotFoundException();
 * 
 * logger.info("accounts id: " + accounts.getId()); profiles =
 * dataServices.getProfileByAccountId(accounts);
 * 
 * try {
 * 
 * CustomUser customUser = (CustomUser) SecurityContextHolder
 * .getContext().getAuthentication().getPrincipal();
 * 
 * logger.info("ID: ....> " + customUser.getId());
 * 
 * } catch (Exception e) { logger.info(e); }
 * 
 * if (profiles == null) throw new EntityNotFoundException();
 * 
 * Set<String> rolesSet = new HashSet<>(); for (UserRole role :
 * accounts.getUserRole()) { rolesSet.add(role.getRole()); }
 * 
 * customProfile = new CustomProfile(profiles.getFirstName(),
 * profiles.getLastName(), profiles.getMiddleInitial(), profiles.getSex(),
 * profiles.getBdMonth(), profiles.getBdDay(), profiles.getBdYear(),
 * profiles.getEmail(), profiles.getAlternateEmail(), profiles.getPhoneHome(),
 * profiles.getPhoneCell(), profiles.getPhoneOffice(), profiles.getBiography(),
 * profiles.getPhotoLink(), profiles.getLinkTwitter(),
 * profiles.getLinkFacebok(), profiles.getLinkGooglePlus(),
 * profiles.getLinkLinkedin(), profiles.getLinkWebsite(),
 * profiles.getLanguage(), profiles.getTimeZone(), profiles.getDateFormat(),
 * profiles.getVideoPlayers(), profiles.getProfileAccess(),
 * profiles.getHighestDegree(), profiles.getHobbies(), rolesSet);
 * 
 * return customProfile; }
 * 
 * 
 * Update User Profile
 * 
 * @RequestMapping(value = "/profile", method = RequestMethod.PUT, consumes =
 * MediaType.APPLICATION_JSON_VALUE)
 * 
 * @ResponseBody public Status addEmployee(@RequestBody Profiles profiles)
 * throws HibernateException, Exception { Accounts accounts ; Profiles
 * existingProfile; if (profiles == null) { throw new
 * EmptyFormElementsException(); }
 * 
 * // need a little optimization List<Errors> errorsList =
 * editProfileFormValidation .validateForm(profiles); if
 * (!editProfileFormValidation.validateForm(profiles).isEmpty()) { return new
 * Status(HttpStatus.NOT_ACCEPTABLE.toString(),
 * env.getProperty("exception.validationfailed"), errorsList);
 * 
 * }
 * 
 * UserDetails userDetails = (UserDetails) SecurityContextHolder
 * .getContext().getAuthentication().getPrincipal();
 * 
 * String username = userDetails.getUsername();
 * 
 * accounts = dataServices.findByUserName(username);
 * 
 * profiles.setEmail(accounts.getAccountEmail());
 * profiles.setAccounts(accounts);
 * 
 * existingProfile = dataServices.getProfileByAccountId(accounts);
 * 
 * if (existingProfile == null) { throw new SomethingWentWrongException(); }
 * 
 * profiles.setId(existingProfile.getId());
 * 
 * dataServices.updateEntity(profiles);
 * 
 * return new Status(HttpStatus.ACCEPTED.toString(),
 * env.getProperty("profile.updated"));
 * 
 * }
 * 
 * 
 * Update User Profile
 * 
 * @RequestMapping(value = "/my-courses", method = RequestMethod.GET)
 * 
 * @ResponseBody public List<CoursesEnrollements>
 * myEnrollerCourses(@RequestParam(required = false, defaultValue =
 * ApplicationConstants.PAGE) Integer page,
 * 
 * @RequestParam(required = false, defaultValue = ApplicationConstants.PER_PAGE)
 * Integer perPage) throws HibernateException, Exception { CustomUser customUser
 * = (CustomUser) SecurityContextHolder.getContext()
 * .getAuthentication().getPrincipal();
 * 
 * List<CoursesEnrollements> coursesEnrollements = customService
 * .getEnrolledCoursesByUserId(customUser.getId(), page, perPage); //TODO: need
 * to remove this check for similarity among APIs if (coursesEnrollements ==
 * null || coursesEnrollements.isEmpty()) throw new EntityNotFoundException();
 * 
 * List<CoursesEnrollements> coursesEnrollementsNew = new ArrayList<>(); for
 * (CoursesEnrollements enrollementsDB : coursesEnrollements) {
 * CoursesEnrollements enrollements = new CoursesEnrollements(
 * enrollementsDB.getId(), enrollementsDB.getCourseId(),
 * enrollementsDB.getSessionId(), enrollementsDB.getGroupId(),
 * enrollementsDB.getProgramId(), enrollementsDB.isPaid(),
 * enrollementsDB.getCost(), enrollementsDB.getCurrency(),
 * enrollementsDB.isEthicPolicyAccepted(),
 * enrollementsDB.isEthicPolicyAccepted(), enrollementsDB.isFavorite(),
 * enrollementsDB.isArchive(), enrollementsDB.isComplete() );
 * coursesEnrollementsNew.add(enrollements); }
 * 
 * return coursesEnrollementsNew;
 * 
 * }
 * 
 * @RequestMapping("/roles")
 * 
 * @ResponseBody public Set<String> getRoles() throws HibernateException,
 * Exception {
 * 
 * CustomUser customUser = (CustomUser) SecurityContextHolder.getContext()
 * .getAuthentication().getPrincipal(); Set<String> rolesSet = new HashSet<>();
 * List<UserRole> rolesList = customService .getUserRole(customUser.getId());
 * for (UserRole role : rolesList) { rolesSet.add(role.getRole()); } return
 * rolesSet; }
 * 
 * @RequestMapping("/reset-password/{newPassword}")
 * 
 * @ResponseBody public Status resetpassowrd(@PathVariable String newPassword) {
 * 
 * CustomUser customUser = (CustomUser) SecurityContextHolder.getContext()
 * .getAuthentication().getPrincipal();
 * 
 * Accounts accounts = null; try { accounts =
 * dataServices.findByUserName(customUser .getUsername());
 * 
 * if (!accounts.getPassword() .equals(utils.getHashPassword(oldPassword)))
 * return new Status(HttpStatus.ACCEPTED.toString(),
 * "Old password not correct");
 * 
 * 
 * accounts.setPassword(utils.getHashPassword(newPassword)); } catch (Exception
 * e) { logger.info(e); throw new SomethingWentWrongException(); }
 * 
 * try { dataServices.updateUserEntity(accounts); } catch (Exception e) {
 * logger.info(e); throw new SomethingWentWrongException(); }
 * 
 * return new Status(HttpStatus.ACCEPTED.toString(),
 * env.getProperty("password.changed")); }
 * 
 * @RequestMapping("/change-password/{oldPassword}/{newPassword}")
 * 
 * @ResponseBody public Status changePassowrd(@PathVariable String
 * oldPassword, @PathVariable String newPassword) {
 * 
 * CustomUser customUser = (CustomUser)
 * SecurityContextHolder.getContext().getAuthentication().getPrincipal();
 * 
 * Accounts accounts = null; try { accounts =
 * dataServices.findByUserName(customUser.getUsername());
 * 
 * if(!utils.checkPassword(oldPassword, accounts.getPassword())){ return new
 * Status(HttpStatus.ACCEPTED.toString(),
 * env.getProperty("wrong.old.password")); }
 * 
 * accounts.setPassword(utils.getHashPassword(newPassword));
 * dataServices.updateUserEntity(accounts);
 * 
 * } catch (Exception e) { logger.info(e); throw new
 * SomethingWentWrongException(); }
 * 
 * return new Status(HttpStatus.ACCEPTED.toString(),
 * env.getProperty("password.changed")); }
 * 
 * @RequestMapping(value = "/profile/node", method = RequestMethod.GET)
 * 
 * @ResponseBody public CustomProfileForNodeApp getUserProfileForNodeApp()
 * throws HibernateException, Exception { Accounts accounts; Profiles profiles;
 * CustomProfileForNodeApp customProfileForNodeApp;
 * 
 * CustomUser customUser = (CustomUser) SecurityContextHolder
 * .getContext().getAuthentication().getPrincipal();
 * 
 * accounts = dataServices.findByUserName(customUser.getUsername()); if
 * (accounts == null) throw new EntityNotFoundException();
 * 
 * profiles = dataServices.getProfileByAccountId(accounts);
 * 
 * if (profiles == null) throw new EntityNotFoundException();
 * 
 * Set<String> rolesSet = new HashSet<>(); for (UserRole role :
 * accounts.getUserRole()) { rolesSet.add(role.getRole()); }
 * 
 * CustomProfile customProfile = new CustomProfile(profiles.getFirstName(),
 * profiles.getLastName(), profiles.getMiddleInitial(), profiles.getSex(),
 * profiles.getBdMonth(), profiles.getBdDay(), profiles.getBdYear(),
 * profiles.getEmail(), profiles.getAlternateEmail(), profiles.getPhoneHome(),
 * profiles.getPhoneCell(), profiles.getPhoneOffice(), profiles.getBiography(),
 * profiles.getPhotoLink(), profiles.getLinkTwitter(),
 * profiles.getLinkFacebok(), profiles.getLinkGooglePlus(),
 * profiles.getLinkLinkedin(), profiles.getLinkWebsite(),
 * profiles.getLanguage(), profiles.getTimeZone(), profiles.getDateFormat(),
 * profiles.getVideoPlayers(), profiles.getProfileAccess(),
 * profiles.getHighestDegree(), profiles.getHobbies(), rolesSet);
 * 
 * String userType = "U";
 * 
 * if(rolesSet.contains("ROLE_COURSE_ADMIN")){ userType = "P"; }
 * 
 * 
 * if(accounts.getNodeKey() == null || accounts.getNodeKey().isEmpty()){
 * accounts.setNodeKey(StringToHex.convertStringToHex(accounts.getAccountEmail()
 * )); dataServices.updateEntity(accounts); }
 * 
 * customProfileForNodeApp = new CustomProfileForNodeApp(accounts.getNodeKey(),
 * customProfile.getFirstName(), customProfile.getLastName(),
 * customProfile.getSex(), customProfile.getPhotoLink(),
 * customProfile.getEmail(), customProfile.getPhoneCell(), userType);
 * 
 * return customProfileForNodeApp; }
 * 
 * @RequestMapping(value = "/profile/node/{nodeKey}", method =
 * RequestMethod.GET)
 * 
 * @ResponseBody public CustomProfileForNodeApp
 * getUserProfileByNodeKey(@PathVariable String nodeKey) throws
 * HibernateException, Exception { Accounts accounts; Profiles profiles;
 * CustomProfileForNodeApp customProfileForNodeApp;
 * 
 * accounts = customService.getAccountByNodeKey(nodeKey); if (accounts == null)
 * throw new EntityNotFoundException();
 * 
 * profiles = dataServices.getProfileByAccountId(accounts);
 * 
 * if (profiles == null) throw new EntityNotFoundException();
 * 
 * Set<String> rolesSet = new HashSet<>(); for (UserRole role :
 * accounts.getUserRole()) { rolesSet.add(role.getRole()); }
 * 
 * CustomProfile customProfile = new CustomProfile(profiles.getFirstName(),
 * profiles.getLastName(), profiles.getMiddleInitial(), profiles.getSex(),
 * profiles.getBdMonth(), profiles.getBdDay(), profiles.getBdYear(),
 * profiles.getEmail(), profiles.getAlternateEmail(), profiles.getPhoneHome(),
 * profiles.getPhoneCell(), profiles.getPhoneOffice(), profiles.getBiography(),
 * profiles.getPhotoLink(), profiles.getLinkTwitter(),
 * profiles.getLinkFacebok(), profiles.getLinkGooglePlus(),
 * profiles.getLinkLinkedin(), profiles.getLinkWebsite(),
 * profiles.getLanguage(), profiles.getTimeZone(), profiles.getDateFormat(),
 * profiles.getVideoPlayers(), profiles.getProfileAccess(),
 * profiles.getHighestDegree(), profiles.getHobbies(), rolesSet);
 * 
 * String userType = "U";
 * 
 * if(rolesSet.contains("ROLE_COURSE_ADMIN")){ userType = "P"; }
 * 
 * 
 * if(accounts.getNodeKey() == null || accounts.getNodeKey().isEmpty()){
 * accounts.setNodeKey(StringToHex.convertStringToHex(accounts.getAccountEmail()
 * )); dataServices.updateEntity(accounts); }
 * 
 * customProfileForNodeApp = new CustomProfileForNodeApp(accounts.getNodeKey(),
 * customProfile.getFirstName(), customProfile.getLastName(),
 * customProfile.getSex(), customProfile.getPhotoLink(),
 * customProfile.getEmail(), customProfile.getPhoneCell(), userType);
 * 
 * return customProfileForNodeApp; } }
 */