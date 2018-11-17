package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.annotation.Resource;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.sql.DataSource;
import javax.transaction.NotSupportedException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

import org.apache.derby.jdbc.ClientDataSource;
import org.junit.jupiter.api.Test;

import com.ptt.entities.QuestionaireAnswer;
import com.ptt.entities.QuestionaireItem;
import com.ptt.entities.Tester;

class EntityTests {
  @PersistenceContext
  private EntityManager em;
  @Resource
  private UserTransaction tx;
  
  @Test
  void TesterCreationTest() {
    //Create a Tester -->Expected Result: crates an entry in userdata table and in session table

  }
  
  @Test
  void TesterDeletionTest() {

    // create Event-Log-Entries associated with this tester and his session
    // delete the tester -->Session entry and all event-logs associated with this 
    // user must be deleted as well 
  }
  
  @Test
  void TestCreationTest() {
    //Create a Tester -->Expected Result: creates an entries in tests table, tasks-table and milestones, retrieval quizes
    // milestones and questionaire-Items
  }
  
  @Test
  void TestDeletionTest() {
    //Delete a Test -->Expected Result: deletes all entries associated with this test 
    //in tests-, tasks-, retrieval_quizes-, milestones- and questionaire-items tables 
  }
  
  @Test
  void questionaireItemDeletionTest() {
    //Delete a  QuestionaireItem-->Expected Result: deletes all entries associated with this questionaire-
    //item in the question_answers-table
  }
  
  @Test
  void questionairePersistenceTest() {
    try {
      ClientDataSource ds = new ClientDataSource(); 
      Context context = new InitialContext();
      Class.forName("org.apache.derby.jdbc.ClientDriver");
      ds = (ClientDataSource) context.lookup("java:/MySqlDS");
      Connection conn = ds.getConnection();
      
      PreparedStatement pStmt = conn.prepareStatement("select *, COUNT(*) from question_answers");
      
      ResultSet res = pStmt.executeQuery();
      res.next();
      int count1 =res.getInt(1); 
      
      tx.begin();
      Query q = em.createQuery("SELECT q FROM QuestionaireItem q WHERE itemId = 1");
      QuestionaireItem qItem = (QuestionaireItem) q.getResultList().get(0);
      QuestionaireAnswer qAnswer = new QuestionaireAnswer();
      Query q1 = em.createQuery("SELECT t FROM Tester t WHERE testerId = 1");
      Tester t = (Tester) q1.getSingleResult();
      qAnswer.setValue("gender:male");
      qAnswer.setItemId(qItem);
      qAnswer.setUserId(t);
      em.persist(qAnswer);
      
      
      PreparedStatement pStmt2 = conn.prepareStatement("select *, COUNT(*) from question_answers");
      ResultSet res2 = pStmt2.executeQuery();
      int count2 = res.getInt(1);
      
      
      assert(count2 - count1 == 2);
    } catch (NotSupportedException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (SystemException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (NamingException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    
    
  }


}
