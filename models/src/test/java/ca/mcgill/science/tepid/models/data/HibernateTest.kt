package ca.mcgill.science.tepid.models.data

import ca.mcgill.science.tepid.models.bindings.TepidDb
import org.hibernate.annotations.TypeDef
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import java.io.Serializable
import java.util.*
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
) : TepidDb() {

}

@Embeddable
data class TestEmbeddable(var data:String) : Serializable

@Entity
data class TestEmbedding(
    @Access(AccessType.FIELD)
    @ElementCollection(targetClass = TestEmbeddable::class)
    var datas: List<TestEmbeddable> = emptyList()
) : TepidDb()

@Entity
data class TestImmutableField(@Id val data:String) : Serializable

@Entity
data class TestListWithVal(
    @Access(AccessType.FIELD)
    @ElementCollection(targetClass = TestImmutableField::class)
    var datas: List<TestImmutableField> = emptyList()
) : TepidDb()

@Embeddable
data class TestImmutableFieldEmbeddable(@Access(AccessType.FIELD) val data:String) : Serializable

@Entity
data class TestListWithValEmbeddable(
        @Access(AccessType.FIELD)
        @Embedded
        @ElementCollection(targetClass = TestImmutableFieldEmbeddable::class)
        var datas: List<TestImmutableFieldEmbeddable> = emptyList()
) : TepidDb()

@Entity
data class TestEntity(
        @Column(nullable = false)
        var content: String = ""
) : TepidDb()

@Entity
data class TestForeignKey(
        @Access(AccessType.FIELD)
        @ManyToOne(fetch = FetchType.EAGER)
        var datum : FullUser
) : TepidDb()

class HibernateTest {

    @Test
    fun testAddObject(){

        val test = TestEntity(content = "t")
        persist(test)

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


    @Test
    fun testFk(){
        val embed0 = FullUser(shortUser = "TESTENTITY")
        val e0 = TestForeignKey(datum = embed0)
        e0._id = "TEST"

        persist(embed0)
        persist(e0)

        val r0 = em.find(TestForeignKey::class.java,"TEST")

        assertNotNull(r0)
        assertEquals(e0, r0)


    }

    fun <C: TepidDb> persist (obj:C){
        em.transaction.begin()
        if(!obj._id.isNullOrBlank()){
//            em.merge(obj)
        } else {
            obj._id = UUID.randomUUID().toString()
        }
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
    fun testFullUserGroups(){
        val test = FullUser(shortUser = "shortUname", groups = setOf("G1", "G2"))
        test._id="TEST"

        persist(test)

        val retrieved = em.find(FullUser::class.java, "TEST")
        assertNotNull(retrieved)
        assertEquals(test.groups, retrieved.groups)

        // repersist
        em.detach(test)
        em.close()

        em = emf.createEntityManager()

        println("===========================")

        val newEm = emf.createEntityManager()
        test.groups = setOf("G1", "G2", "G3")

        newEm.transaction.begin()
        newEm.merge(test)
        newEm.transaction.commit()

        val retrieved1 = newEm.find(FullUser::class.java, "TEST")

        println("===========================")


        assertNotNull(retrieved1)
        assertEquals(test.groups, retrieved1.groups)


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
    fun testReadFullSession(){
        val testFullUser = FullUser(shortUser = "shortUname")

        persist(testFullUser)
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
        val truncatable = listOf(

                TestListWithVal::class.java,
                TestImmutableField::class.java,

                TestEntity::class.java,
                TestList::class.java,
                TestListedEntity::class.java,
                TestForeignKey::class.java,
                FullSession::class.java,
                PrintJob::class.java,
                FullDestination::class.java,
                DestinationTicket::class.java)
        truncatable.forEach{truncate(it)}
        val removal = listOf(
                TestEmbedding::class.java,
                TestListWithValEmbeddable::class.java,
                FullUser::class.java
        )
        removal.forEach {
            deleteAllIndividually(it)
        }
    }


    companion object {
        internal fun <T> truncate(classParameter: Class<T>){
            em.transaction.begin()
            em.flush()
            em.clear()
            em.createQuery("DELETE FROM ${classParameter.simpleName} e").executeUpdate()
            em.transaction.commit()
        }

        internal fun<T> deleteAllIndividually(classParameter: Class<T>){
            em.transaction.begin()
            val l : List<T> = em.createQuery("SELECT c FROM ${classParameter.simpleName} c", classParameter).resultList
            l.forEach {
                em.remove(it)
            }
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