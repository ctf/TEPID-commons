package ca.mcgill.science.tepid.models.data

import ca.mcgill.science.tepid.models.bindings.TepidDb
import ca.mcgill.science.tepid.models.bindings.TepidDbDelegate
import org.hibernate.annotations.TypeDef
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import java.io.Serializable
import javax.persistence.*

@TypeDef(
        name = "ListTest",
        typeClass = ArrayList::class
)

@Entity
data class TestListedEntity(@Id var data:String) : Serializable

@Entity
data class TestList(
    @Access(AccessType.FIELD)
    @OneToMany(targetEntity = TestListedEntity::class)
    var datas: List<TestListedEntity> = emptyList()
) : @EmbeddedId TepidDb by TepidDbDelegate() {

}

@Embeddable
data class TestEmbeddable(var data:String) : Serializable

@Entity
data class TestEmbedding(
    @Access(AccessType.FIELD)
    @ElementCollection(targetClass = TestEmbeddable::class)
    var datas: List<TestEmbeddable> = emptyList()
) : @EmbeddedId TepidDb by TepidDbDelegate()

@Entity
data class TestImmutableField(@Id val data:String) : Serializable

@Entity
data class TestListWithVal(
    @Access(AccessType.FIELD)
    @ElementCollection(targetClass = TestImmutableField::class)
    var datas: List<TestImmutableField> = emptyList()
) : @EmbeddedId TepidDb by TepidDbDelegate()

@Embeddable
data class TestImmutableFieldEmbeddable(@Access(AccessType.FIELD) val data:String) : Serializable

@Entity
data class TestListWithValEmbeddable(
        @Access(AccessType.FIELD)
        @Embedded
        @ElementCollection(targetClass = TestImmutableFieldEmbeddable::class)
        var datas: List<TestImmutableFieldEmbeddable> = emptyList()
) : @EmbeddedId TepidDb by TepidDbDelegate()

@Entity
data class TestEntity(
        @Column(nullable = false)
        var content: String = ""
) : @EmbeddedId TepidDb by TepidDbDelegate()

class HibernateTest {

    @Test
    fun testAddObject(){

        em.transaction.begin()
        val test = TestEntity(content = "t")
        em.persist(test)
        em.transaction.commit()
        val te = em.find(TestEntity::class.java, test._id)

        assertNotNull(te)
        assertEquals(test, te)
        println(te._id)
    }

    fun <C : TepidDb, D>collectionTest(collectionClassFactory: (List<D>) -> C, collectedClassFactory: (String) -> D, persistCollected:Boolean = false){
        em.transaction.begin()
        val e0 = collectedClassFactory("0")
        val e1 = collectedClassFactory("1")
        val test = collectionClassFactory(listOf(e0, e1))
        test._id = "TEST"
        if (persistCollected) {
            em.persist(e0)
            em.persist(e1)
        }
        em.persist(test)
        em.transaction.commit()
        val retrieved = em.find(test::class.java, test._id)

        assertNotNull(retrieved)
        assertEquals(test, retrieved)
    }

    @Test
    fun testAddListingEntity(){
        collectionTest<TestList, TestListedEntity>({ l -> TestList(l)}, { s-> TestListedEntity(s)}, true)
    }

    @Test
    fun testAddEmbeddingObject(){
        collectionTest<TestEmbedding, TestEmbeddable>({l -> TestEmbedding(l) }, { s-> TestEmbeddable(s) }, false)
    }

    @Test
    fun testAddObjectWithImmutableField(){
        collectionTest<TestListWithVal, TestImmutableField>({l -> TestListWithVal(l) }, { s -> TestImmutableField(s) }, true)
    }

    @Test
    fun testAddObjectWithImmutableFieldEmbeddable(){
        collectionTest<TestListWithValEmbeddable, TestImmutableFieldEmbeddable>({l -> TestListWithValEmbeddable(l) }, { s -> TestImmutableFieldEmbeddable(s) }, false)
    }

    fun <C> persist (obj:C){
        em.transaction.begin()
        em.persist(obj)
        em.transaction.commit()
    }

    fun <C: TepidDb> crudTest (obj: C){
        persist(obj)

        val retrieved : C = em.find(obj::class.java, obj._id)
        assertNotNull(retrieved)
        assertEquals(obj, retrieved)
    }

    @Test
    fun testAddFullUser(){
        val test = FullUser(shortUser = "shortUname")
        test._id="TEST"
        crudTest(test)
    }

    @Test
    fun testAddFullSession(){
        val testFullUser = FullUser(shortUser = "shortUname")

        persist(testFullUser)

        val testFullSession = FullSession("testRole", testFullUser, 10000, false)
        testFullSession._id="TEST"

        crudTest(testFullSession)
    }

    @Test
    fun testAddPrintJob(){
        val testPrintJob = PrintJob(name="Print Print")
        testPrintJob._id = "TEST"
        crudTest(testPrintJob)
    }

    @Test
    fun testIsRefunded(){
        val testPrintJob = PrintJob(name="TestIsRefunded", isRefunded = true)
        testPrintJob._id = "TEST"
        persist(testPrintJob)

        val retrieved = em.find(PrintJob::class.java, testPrintJob._id)
        assertNotNull(retrieved)
        assertEquals(retrieved.isRefunded, true)

        val retrievedByQuery = em.createQuery("SELECT c FROM PrintJob c WHERE c.isRefunded = TRUE", PrintJob::class.java).singleResult
        assertNotNull(retrievedByQuery)
        assertEquals(retrievedByQuery.isRefunded, true)
    }

    @Test
    fun testAddDestinationTicket(){
        val testFullUser = FullUser(shortUser = "shortUname")
        persist(testFullUser)
        val testDestinationTicket = DestinationTicket(user=testFullUser.toUser())
        crudTest(testDestinationTicket)
    }

    @Test
    fun testAddFullDestination(){
        val testFullDestination = FullDestination(name="testName")
        crudTest(testFullDestination)
    }

    @Test
    fun testAddFullDestinationWithTicket(){

        val testFullUser = FullUser(shortUser = "shortUname")
        persist(testFullUser)
        var testFullDestination = FullDestination(name="testName")
        persist(testFullDestination)

        val testDestinationTicket = DestinationTicket(reason="NO REASON AT ALL", user = testFullUser.toUser())
        testDestinationTicket._id = "testDestinationId"
        persist(testDestinationTicket)
        testFullDestination.ticket = testDestinationTicket

        crudTest(testFullDestination)

    }

    @Test
    fun testAddMarqueeData(){
        val testMarqueeData = MarqueeData("TITLE", listOf("A", "B"))
        crudTest(testMarqueeData)
    }

    @Test
    fun testAddPrintQueue(){
        val testPrintQueue = PrintQueue("fiddy-fiddy", "false", "Queueueueue", listOf("A", "B"))
        crudTest(testPrintQueue)
    }


    /*@BeforeEach
    fun initialiseDb(){
        val session = em.unwrap(Session::class.java);
        session.doWork(Work { c: Connection ->
            val script = File(this::class.java.classLoader.getResource("content.sql").file)
            RunScript.execute(c, FileReader(script))
        })
    }*/

    @AfterEach
    fun truncateUsed(){
        val u = listOf(FullSession::class.java, FullDestination::class.java, DestinationTicket::class.java, FullUser::class.java)
        u.forEach{truncate(it)}
    }


    companion object {
        internal fun <T> truncate(classParameter: Class<T>){
            em.transaction.begin()
            em.flush()
            em.clear()
            em.createQuery("DELETE FROM ${classParameter.simpleName} e").executeUpdate()
            em.transaction.commit()
        }

        lateinit var emf: EntityManagerFactory
        lateinit var em: EntityManager

        @JvmStatic
        @BeforeAll
        fun initTest() {
            emf = Persistence.createEntityManagerFactory("hibernate-pu-test")
            em = emf.createEntityManager()
        }

        @JvmStatic
        @AfterAll
        fun tearTest(){
            em.clear()
            em.close()
            emf.close()
        }
    }
}