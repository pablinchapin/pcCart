/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pablinchapin.picarto.core;

import com.pablinchapin.picarto.core.common.EmailService;
import java.sql.SQLException;
import javax.sql.DataSource;
import static org.junit.Assert.assertTrue;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 *
 * @author pvargas
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = PiCartoCoreApplication.class)
public class PicartoCoreApplicationTest {
    
    
    @Autowired 
    DataSource dataSource;
    
    @Autowired
    EmailService emailService;
    
    
    @Test
    public void testDummy() throws SQLException{
        
        String schema = dataSource.getConnection().getCatalog();
        
        assertTrue("picarto".equalsIgnoreCase(schema));
    
    
    }
    
    
    @Test
    //@Ignore
    public void testSendEmail(){
        emailService.sendEmail("pablovargasmelgar@gmail.com", "Prueba Picarto", "This is a test emial from Picarto");
    }
    
    
}
