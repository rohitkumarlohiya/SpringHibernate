/*
 * package com.mcloud.controllers;
 * 
 * import java.util.Date; import java.util.List; import java.util.Map; import
 * java.util.concurrent.TimeUnit;
 * 
 * import org.apache.log4j.Logger; import org.hibernate.HibernateException;
 * import org.hibernate.exception.ConstraintViolationException; import
 * org.springframework.beans.factory.annotation.Autowired; import
 * org.springframework.beans.factory.annotation.Qualifier; import
 * org.springframework.context.annotation.PropertySource; import
 * org.springframework.core.env.Environment; import
 * org.springframework.http.HttpStatus; import
 * org.springframework.http.MediaType; import
 * org.springframework.stereotype.Controller; import
 * org.springframework.web.bind.annotation.PathVariable; import
 * org.springframework.web.bind.annotation.RequestBody; import
 * org.springframework.web.bind.annotation.RequestMapping; import
 * org.springframework.web.bind.annotation.RequestMethod; import
 * org.springframework.web.bind.annotation.RequestParam; import
 * org.springframework.web.bind.annotation.ResponseBody;
 * 
 * import com.jcraft.jsch.JSchException; import com.jcraft.jsch.SftpException;
 * import com.mcloud.entities.AbstractEntity; import
 * com.mcloud.exceptions.custom.EmptyFormElementsException; import
 * com.mcloud.exceptions.custom.EntityNotFoundException; import
 * com.mcloud.exceptions.custom.InvalidConfirmationCode; import
 * com.mcloud.exceptions.custom.InvalidLink; import
 * com.mcloud.exceptions.custom.SomethingWentWrongException; import
 * com.mcloud.exceptions.custom.UserAlreadyExists; import
 * com.mcloud.formvalidation.rest.SignupFormValidation; import
 * com.mcloud.models.commons.Errors; import com.mcloud.models.commons.Status;
 * import com.mcloud.models.login.Accounts; import
 * com.mcloud.models.login.UserRole; import com.mcloud.services.CustomServices;
 * import com.mcloud.services.DataServices; import
 * com.mcloud.services.MailBodyTempletes; import
 * com.mcloud.services.MailService; import com.mcloud.sftp.courses.CoursesSftp;
 * import com.mcloud.utils.StringToHex; import com.mcloud.utils.Utils;
 * 
 * @Controller
 * 
 * @RequestMapping("api/login")
 * 
 * @PropertySource("classpath:message.properties") public class
 * LoginControllerRest {
 * 
 * @Autowired Utils utils;
 * 
 * @Autowired Environment env;
 * 
 * @Autowired DataServices<AbstractEntity> dataServices;
 * 
 * @Autowired CustomServices customServices;
 * 
 * @Autowired SignupFormValidation signupFormValidation;
 * 
 * @Autowired
 * 
 * @Qualifier("mailService") MailService mailService;
 * 
 * @Autowired MailBodyTempletes mailBodyTempletes;
 * 
 * static final Logger logger = Logger.getLogger(LoginControllerRest.class);
 * 
 * 
 * need to handle email uniqueness properly and more optimization
 * 
 * @RequestMapping(value = "/register", method = RequestMethod.POST, consumes =
 * MediaType.APPLICATION_JSON_VALUE) public @ResponseBody Status
 * addEmployee(@RequestBody Accounts accounts) {
 * 
 * if (accounts == null) { throw new EmptyFormElementsException(); }
 * 
 * // need a little optimization List<Errors> errorsList =
 * signupFormValidation.validateForm(accounts); if
 * (!signupFormValidation.validateForm(accounts).isEmpty()) { return new
 * Status(HttpStatus.NOT_ACCEPTABLE.toString(),
 * env.getProperty("exception.validationfailed"), errorsList);
 * 
 * }
 * 
 * accounts.setPassword(utils.getHashPassword(accounts.getPassword()));
 * accounts.setActivated(false);
 * accounts.setConfirmation(utils.confirmationString());
 * accounts.setNodeKey(StringToHex.convertStringToHex(accounts.getAccountEmail()
 * ));
 * 
 * try { dataServices.addUser(accounts, new UserRole(accounts, "ROLE_USER")); }
 * catch (ConstraintViolationException e) { e.printStackTrace(); throw new
 * UserAlreadyExists(); } catch (Exception e) { e.printStackTrace(); throw new
 * SomethingWentWrongException(); }
 * 
 * // confirmation mail String verificationLink =
 * "http://"+env.getProperty("admin.app.link")+"/minf/#!/login?email="+accounts.
 * getAccountEmail()+"&authcode="+accounts.getConfirmation();
 * 
 * String[] to = { accounts.getAccountEmail() };
 * 
 * String bodyContent = mailBodyTempletes
 * .getEmailTemplete("email_confirmation") .replace("USER_NAME",
 * accounts.getAccountEmail()) .replace("VERIFICATION_LINK",verificationLink);
 * 
 * mailService.sendMail(env.getProperty("admin.email"), to,
 * env.getProperty("register.subject"), bodyContent);
 * 
 * return new Status(HttpStatus.ACCEPTED.toString(),
 * env.getProperty("register.success"));
 * 
 * }
 * 
 * @RequestMapping(value = "/", method = RequestMethod.GET)
 * 
 * @ResponseBody public Status verifyEmailV2(@RequestParam(required = true)
 * String email,@RequestParam(required = true) String authcode) {
 * 
 * Accounts accounts = null; try { accounts =
 * dataServices.findByUserName(email); } catch (Exception e) {
 * e.printStackTrace(); throw new SomethingWentWrongException(); }
 * 
 * if (accounts == null) { throw new EntityNotFoundException(); }
 * 
 * // check if link is expired long days = getDifferenceDays(new Date(),
 * accounts.getCD()); if (days >
 * Integer.valueOf(env.getProperty("link.valid.no.of.days"))) { throw new
 * InvalidLink("Invalid link"); }
 * 
 * if (!accounts.getConfirmation().equalsIgnoreCase(authcode)) throw new
 * InvalidConfirmationCode();
 * 
 * accounts.setActivated(true); try { dataServices.activateUser(accounts); }
 * catch (Exception e) { e.printStackTrace(); throw new
 * SomethingWentWrongException(); }
 * 
 * try { CoursesSftp.executeForNodeApp(accounts.getNodeKey()); } catch
 * (JSchException e) { e.printStackTrace(); throw new
 * SomethingWentWrongException(); } catch (SftpException e) {
 * e.printStackTrace(); throw new SomethingWentWrongException(); }
 * 
 * return new Status(HttpStatus.ACCEPTED.toString(),
 * env.getProperty("account.activated"));
 * 
 * }
 * 
 * @RequestMapping("emailconfirm/{email}/{code}")
 * 
 * @ResponseBody public Status verifyEmail(@PathVariable String email,
 * 
 * @PathVariable String code) {
 * 
 * Accounts accounts = null; try { accounts =
 * dataServices.findByUserName(email); } catch (Exception e) {
 * e.printStackTrace(); throw new SomethingWentWrongException(); }
 * 
 * if (accounts == null) { throw new EntityNotFoundException(); }
 * 
 * // check if link is expired long days = getDifferenceDays(new Date(),
 * accounts.getCD()); if (days >
 * Integer.valueOf(env.getProperty("link.valid.no.of.days"))) { throw new
 * InvalidLink("Invalid link"); }
 * 
 * if (!accounts.getConfirmation().equalsIgnoreCase(code)) throw new
 * InvalidConfirmationCode();
 * 
 * accounts.setActivated(true); try { dataServices.activateUser(accounts); }
 * catch (Exception e) { e.printStackTrace(); throw new
 * SomethingWentWrongException(); }
 * 
 * return new Status(HttpStatus.ACCEPTED.toString(),
 * env.getProperty("account.activated"));
 * 
 * }
 * 
 * public static long getDifferenceDays(Date d1, Date d2) { long diff =
 * Math.abs(d2.getTime() - d1.getTime()); return TimeUnit.DAYS.convert(diff,
 * TimeUnit.MILLISECONDS); }
 * 
 * @RequestMapping("forgetpassword")
 * 
 * @ResponseBody public Status forgetPassword(@RequestParam String
 * email, @RequestParam(defaultValue = "USER") String appType) {
 * 
 * if (email == null) throw new SomethingWentWrongException();
 * 
 * email = email.trim();
 * 
 * String conformString = utils.confirmationString();
 * 
 * try { dataServices.updateConfirmation(email, conformString); } catch
 * (Exception e) { e.printStackTrace(); throw new SomethingWentWrongException();
 * }
 * 
 * // sending password change mail String verificationLink = null;
 * if(appType.equalsIgnoreCase("ADMIN")){ verificationLink =
 * "http://"+env.getProperty("admin.app.link")+"/minf/#!/newpassword/"+email+"/"
 * +conformString; } else { verificationLink =
 * "http://"+env.getProperty("user.app.link")+"/minf/#!/newpassword/"+email+"/"+
 * conformString; }
 * 
 * String[] to = { email };
 * 
 * String bodyContent = mailBodyTempletes .getEmailTemplete("forget_password")
 * .replace("USER_NAME", email) .replace("VERIFICATION_LINK",verificationLink);
 * 
 * mailService.sendMail(env.getProperty("admin.email"), to,
 * env.getProperty("passwordchange.subject"), bodyContent);
 * 
 * return new Status(HttpStatus.ACCEPTED.toString(),
 * env.getProperty("passwordchange.success")); }
 * 
 * @RequestMapping("newpassword/{email}/{code}/{newPassword}")
 * 
 * @ResponseBody public Status newPassword(@PathVariable String email,
 * 
 * @PathVariable String code, @PathVariable String newPassword) {
 * 
 * Accounts accounts = null; try { accounts =
 * dataServices.findByUserName(email); } catch (Exception e) {
 * e.printStackTrace(); throw new SomethingWentWrongException(); }
 * 
 * if (accounts == null) { throw new EntityNotFoundException(); }
 * 
 * // check if link is expired long days = getDifferenceDays(new Date(),
 * accounts.getUD()); if (days >
 * Integer.valueOf(env.getProperty("link.valid.no.of.days"))) { throw new
 * InvalidLink(env.getProperty("expire.link")); }
 * 
 * if (!accounts.getConfirmation().equalsIgnoreCase(code)) throw new
 * InvalidConfirmationCode();
 * 
 * accounts.setPassword(utils.getHashPassword(newPassword)); try {
 * dataServices.updateEntity(accounts); } catch (Exception e) {
 * e.printStackTrace(); throw new SomethingWentWrongException(); }
 * 
 * return new Status(HttpStatus.ACCEPTED.toString(),
 * env.getProperty("password.changed")); }
 * 
 * @RequestMapping(value = "/email/verify/v2", method = RequestMethod.GET)
 * public @ResponseBody boolean verifyEmailId(@RequestParam(required=true)
 * String email) throws HibernateException, Exception{
 * 
 * return customServices.isEmailExists(email); }
 * 
 * @RequestMapping(value = "/email/verify/", method = RequestMethod.GET)
 * public @ResponseBody Map<String,Boolean>
 * verifyEmailIdAndCheckActivated(@RequestParam(required=true) String email)
 * throws HibernateException, Exception{
 * 
 * return customServices.verifyEmailIdAndCheckActivated(email); }
 * 
 * @RequestMapping(value = "/resend/activation/link", method =
 * RequestMethod.GET) public @ResponseBody Status
 * resendActicationLink(@RequestParam(required=true) String email) throws
 * HibernateException, Exception{
 * 
 * Accounts accounts = null; try { accounts =
 * dataServices.findByUserName(email); } catch (Exception e) {
 * e.printStackTrace(); throw new SomethingWentWrongException(); }
 * 
 * if (accounts == null) { throw new EntityNotFoundException(); }
 * 
 * accounts.setConfirmation(utils.confirmationString());
 * 
 * dataServices.updateEntity(accounts);
 * 
 * // confirmation mail String verificationLink =
 * "http://"+env.getProperty("admin.app.link")+"/minf/#!/login?email="+accounts.
 * getAccountEmail()+"&authcode="+accounts.getConfirmation();
 * 
 * String[] to = { accounts.getAccountEmail() }; String bodyContent =
 * mailBodyTempletes .getEmailTemplete("email_confirmation")
 * .replace("USER_NAME", accounts.getAccountEmail())
 * .replace("VERIFICATION_LINK",verificationLink);
 * 
 * mailService.sendMail(env.getProperty("admin.email"), to,
 * env.getProperty("register.subject"), bodyContent);
 * 
 * return new Status(HttpStatus.ACCEPTED.toString(),
 * env.getProperty("resend.activation.success")); } }
 */