package ca.mcgill.science.tepid.models.data

import ca.mcgill.science.tepid.models.bindings.TepidDb
import ca.mcgill.science.tepid.models.bindings.TepidDbDelegate
import org.hibernate.annotations.TypeDef
import org.junit.Ignore
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import java.io.Serializable
import javax.persistence.*
import kotlin.jvm.Transient
import kotlin.reflect.KClass
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

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
    fun test(){
        collectionTest<TestList, TestListedEntity>({ l -> TestList(l)}, { s-> TestListedEntity(s)}, true)

    }

    @Test
    fun testAddListingObject(){
        em.transaction.begin()
        val e1 = TestListedEntity("1")
        val e2 = TestListedEntity("2")
        val test = TestList()
        test.datas = listOf(e1,e2)
        em.persist(e1)
        em.persist(e2)
        em.persist(test)
        em.transaction.commit()
        val te = em.find(TestList::class.java, test._id)

        assertNotNull(te)
        assertEquals(test, te)
        println(te._id)
    }

    @Test
    fun testAddEmbeddingObject(){
        em.transaction.begin()
        val e1 = TestEmbeddable("1")
        val e2 = TestEmbeddable("2")
        val test = TestEmbedding(listOf(e1,e2))
        em.persist(test)
        em.transaction.commit()
        val te = em.find(TestEmbedding::class.java, test._id)

        assertNotNull(te)
        assertEquals(test, te)
        println(te._id)
    }

    @Test
    fun testAddObjectWithImmutableField(){
        em.transaction.begin()
        val e1 = TestImmutableField("1")
        val e2 = TestImmutableField("2")
        val test = TestListWithVal(listOf(e1,e2))
        em.persist(e1)
        em.persist(e2)
        em.persist(test)
        em.transaction.commit()
        val te = em.find(TestListWithVal::class.java, test._id)

        assertNotNull(te)
        assertEquals(test, te)
        println(te._id)
    }

    @Test
    fun testAddObjectWithImmutableFieldEmbeddable(){
        em.transaction.begin()
        val e1 = TestImmutableFieldEmbeddable("1")
        val e2 = TestImmutableFieldEmbeddable("2")
        val test = TestListWithValEmbeddable(listOf(e1,e2))
        em.persist(test)
        em.transaction.commit()
        val te = em.find(TestListWithValEmbeddable::class.java, test._id)

        assertNotNull(te)
        assertEquals(test, te)
        println(te._id)
    }

    @Ignore
    @Test
    fun testAddFullUser(){
        em.transaction.begin()
        val testFullUser = FullUser(shortUser = "shortUname")
        em.persist(testFullUser)
        em.transaction.commit()
        val retrievedUser : FullUser = em.find(FullUser::class.java ,testFullUser._id)

        assertNotNull(retrievedUser)
        assertEquals(testFullUser, retrievedUser)
    }

    @Test
    fun testAddCourse(){
        em.transaction.begin()
        val testCourse = Course("TEST101", Season.SUMMER, 1337)
        em.persist(testCourse)
        em.transaction.commit()
        val retrievedCourse = em.find(Course::class.java, Course("TEST101", Season.SUMMER, 1337))

        assertNotNull(retrievedCourse)
        assertEquals(testCourse, retrievedCourse)
        println(retrievedCourse)
    }

    @Test
    fun testQueryCourse(){
        em.transaction.begin()
        val testCourse = Course("TEST101", Season.SUMMER, 1337)
        em.persist(testCourse)
        em.transaction.commit()

        val jpql = "select e from Course e where e.name = :name"

        val retrievedCourse : Course = em.createQuery(jpql, Course::class.java)
                .setParameter("name", "TEST101")
                .singleResult

        println(retrievedCourse)
        assertEquals(testCourse, retrievedCourse)

    }

    /*@BeforeEach
    fun initialiseDb(){
        val session = em.unwrap(Session::class.java);
        session.doWork(Work { c: Connection ->
            val script = File(this::class.java.classLoader.getResource("content.sql").file)
            RunScript.execute(c, FileReader(script))
        })
    }*/

    companion object {
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