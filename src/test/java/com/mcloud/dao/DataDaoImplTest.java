package com.mcloud.dao;
/*
 * package org.firstonlineuniversity.dao;
 * 
 * import static org.junit.Assert.fail;
 * 
 * import javax.transaction.Transactional;
 * 
 * import org.firstonlineuniversity.JunitConfigurationTest; import
 * org.firstonlineuniversity.models.localization.Localization; import
 * org.hibernate.HibernateException; import
 * org.hibernate.exception.ConstraintViolationException; import org.junit.Test;
 * import org.springframework.beans.factory.annotation.Autowired; import
 * org.springframework.test.context.transaction.TransactionConfiguration;
 * 
 * @Transactional
 * 
 * @TransactionConfiguration(defaultRollback=true)
 * //@TestExecutionListeners({TransactionalTestExecutionListener.class}) public
 * class DataDaoImplTest extends JunitConfigurationTest {
 * 
 * @Autowired DataDao dataDao;
 * 
 * //this is not rolling back the databbase
 * 
 * @Test public void testAddEntity() {
 * 
 * Localization localization = new Localization();
 * localization.setKeyText("ssss"); localization.setValueText("qqqq");
 * localization.setBaseText("qqqq"); localization.setApplicationCode("TTTT");
 * localization.setCountryCode("US"); localization.setLanguageCode("en");
 * 
 * try { boolean b = dataDao.addEntity(localization); System.out.println(b); }
 * catch (ConstraintViolationException e) { // TODO Auto-generated catch block
 * e.printStackTrace(); } catch (HibernateException e) { // TODO Auto-generated
 * catch block e.printStackTrace(); } catch (Exception e) { // TODO
 * Auto-generated catch block e.printStackTrace(); }
 * 
 * //fail("Not yet implemented"); }
 * 
 * @Test public void testUpdateEntity() { fail("Not yet implemented"); }
 * 
 * @Test public void testGetEntity() { fail("Not yet implemented"); }
 * 
 * @Test public void testDeleteEntity() { fail("Not yet implemented"); }
 * 
 * @Test public void
 * testGetEntityListClassOfQextendsAbstractEntityIntegerInteger() {
 * fail("Not yet implemented"); }
 * 
 * }
 */