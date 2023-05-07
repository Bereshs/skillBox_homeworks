import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;


public class Main {

    public static void main(String[] args) {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().
                configure("hibernate.cfg.xml").build();
        Metadata metadata = new MetadataSources(registry).getMetadataBuilder().build();
        SessionFactory sessionFactory = metadata.getSessionFactoryBuilder().build();

        Session session = sessionFactory.openSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();

        CriteriaQuery<PurchaseList> criteriaQuery = criteriaBuilder.createQuery(PurchaseList.class);
        Root<PurchaseList> purchaseListRoot = criteriaQuery.from(PurchaseList.class);
        CriteriaQuery<PurchaseList> allPurchase = criteriaQuery.select(purchaseListRoot);
        TypedQuery<PurchaseList> purchaseListTypedQuery = session.createQuery(allPurchase);
        List<PurchaseList> purchaseListList = purchaseListTypedQuery.getResultList();

        purchaseListList.forEach(purchaseList -> {
            String studentName = purchaseList.getStudentName();
            String courseName = purchaseList.getCourseName();
            int studentId = getIdByKey(criteriaBuilder, session, Student.class, "name", studentName);
            int courseId = getIdByKey(criteriaBuilder, session, Course.class, "name", courseName);
            Student student = session.get(Student.class, studentId);
            Course course = session.get(Course.class, courseId);
            Transaction transaction = session.beginTransaction();
            LinkedPurchaseList linkedPurchaseList = new LinkedPurchaseList();
            linkedPurchaseList.setStudent(student);
            linkedPurchaseList.setCourse(course);
            linkedPurchaseList.setId(new LinkedPurchaseListKey(student.getId(), course.getId()));
            session.save(linkedPurchaseList);
            transaction.commit();


        });

        session.close();
        sessionFactory.close();
    }

    public static <T> int getIdByKey(CriteriaBuilder criteriaBuilder, Session session, Class<T> object, String keyName, String value) {
        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(object);
        Root<T> root = criteriaQuery.from(object);
        criteriaQuery.select(root.get("id"));
        criteriaQuery.where(criteriaBuilder.equal(root.get(keyName), value));
        Query query = session.createQuery(criteriaQuery);

        return Integer.parseInt(query.getResultList().get(0).toString());
    }

}
