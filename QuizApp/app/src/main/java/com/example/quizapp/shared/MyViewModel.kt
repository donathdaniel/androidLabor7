package com.example.quizapp.shared

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quizapp.model.BaseModel
import com.example.quizapp.model.Categories
import com.example.quizapp.model.QuestionCategory
import com.example.quizapp.repository.Repository
import kotlinx.coroutines.launch
import retrofit2.Response


data class Question(var answers : MutableList<Pair<String,Boolean>>?, val text : String?)

val hardCodedQuestions: MutableList<Question> = mutableListOf(
    Question(mutableListOf(Pair("14",false),Pair("11",false),Pair("8",false),Pair("5",true)), "One rabbit saw 6 elephants while going towards River. Every elephant saw 2 monkeys are going towards river. Every monkey holds one tortoice in their hands. How many animals are going towards the river?")
    ,Question(mutableListOf(Pair("Light",false),Pair("Bats",false),Pair("Stars",true),Pair("Flights",false)), "They come out at night without being called and are lost in the day without being stolen. What are they?")
    ,Question(mutableListOf(Pair("Maggi",false),Pair("Ericson",false),Pair("Sona",false),Pair("Jason",true)), "A man was murdered in his office. The suspects are Ericson, Maggi, Joel, Benny, Sona, Patick. A calendar found near the man has blood written 6, 4, 9, 10, 11. Who is the killer?")
    ,Question(mutableListOf(Pair("Egyszer",false),Pair("Kétszer",false),Pair("Háromszor",true),Pair("Nyolcszor",false)), "Hányszor 8 88-ból 8*8?")
    ,Question(mutableListOf(Pair("Shoe",false),Pair("Charcoal",true),Pair("Belt",false),Pair("All the above",false)), "What is black when you buy it, red when you use it, and gray when you throw it away?")
    ,Question(mutableListOf(Pair("Money",false),Pair("Luxury items",false),Pair("Brain",false),Pair("Nothing",true)), "Poor people have it.Rich people need it.If you eat it you die.")
    ,Question(mutableListOf(Pair("Mirror",false),Pair("Book",true),Pair("Table",false),Pair("None of the above",false)), "What has a spine but no bones?")
    ,Question(mutableListOf(Pair("Son-in-law",true),Pair("Daughter-in-law",false),Pair("Grand mother",false),Pair("Grand Daughter",false)), "If Theresa's daughter is my daughter's mother, what am I to Theresa?")
)
val hardCodedQuestions2: MutableList<Question> = mutableListOf(
    Question(mutableListOf(Pair("main",false),Pair("fun",true),Pair("function",false),Pair("new",false)), "Which keyword do you use to define a function in Kotlin?")
    ,Question(mutableListOf(Pair("Google",false),Pair("Sun System",false),Pair("IBM",false),Pair("JetBrains",true)), "Kotlin is developed by...")
    ,Question(mutableListOf(Pair(".ko",false),Pair(".jv",false),Pair(".app",false),Pair(".kt",true)), "Which of the below extensions is used to create Kotlin file?")
    ,Question(mutableListOf(Pair("1",false),Pair("2",true),Pair("3",false),Pair("4",false)), "How many types of constructors available in Kotlin?")
    ,Question(mutableListOf(Pair("val person = new Person()",false),Pair("val person = obj Person()",false),Pair("val person = Person()",true),Pair("val person = instace of Person()",false)), "How to create instance of the class in Kotlin?")
    ,Question(mutableListOf(Pair("private",false),Pair("protected",false),Pair("internal",false),Pair("public",true)), "The default visibility of Kotlin class is..")
    ,Question(mutableListOf(Pair("Lambda Function",false),Pair("Range",false),Pair("Sealed Class",false),Pair("Elvis Operator",true)), "Which of the following is used to handle null exceptions in Kotlin?")
    ,Question(mutableListOf(Pair("String",false),Pair("List",true),Pair("Array",false),Pair("Character",false)), "Which of the following is not basic datatypes in Kotlin?")
    ,Question(mutableListOf(Pair("OOP",false),Pair("Procedural Programming",false),Pair("A and B Both",true),Pair("None of the above",false)), "Kotlin language is ___")
    ,Question(mutableListOf(Pair("super",false),Pair("package",false),Pair("static",true),Pair("dynamic",false)), "Which of the following is not reserved keyword in Kotlin?")
    ,Question(mutableListOf(Pair("Top of file",false),Pair("First class in the file",false),Pair("main function",true),Pair("None of the above",false)), "Kotlin programme starts executing from ___")
    ,Question(mutableListOf(Pair("?",false),Pair(":",false),Pair("\"\"",false),Pair("()",true)), "In Kotlin, Inheritance between classes is declared by a symbol ___")
    ,Question(mutableListOf(Pair("Theme Editor",false),Pair("Android SDK Manager",false),Pair("AVD Manager",true),Pair("Virtual Editor",false)), "You can create an emulator to simulate the configuration of a particular type of Android device using a tool like ___.")
    ,Question(mutableListOf(Pair("minSdkVersion",false),Pair("targetSdkVersion",false),Pair("testSdkVersion",false),Pair("compileSdkVersion",true)), "What parameter specifies the Android API level that Gradle should use to compile your app?")
    ,Question(mutableListOf(Pair("Type safety",false),Pair("Data binding",false),Pair("Type validation",false),Pair("Wrong text",true)), "What phrase means that the compiler validates types while compiling?")
    ,Question(mutableListOf(Pair("A fragment has its own lifecycle and receives its own input events.",false),
        Pair("It is not possible to remove a fragment while the activity is running.",true),
        Pair("A fragment is defined in a Kotlin class.\n",false),
        Pair("A fragment's UI is defined in an XML layout file.",false)), "Which of the following is not true about fragments?")
    ,Question(mutableListOf(Pair("Score",false),Pair("LiveData",false),Pair("GameViewModel",false),Pair("Encapsulation",true)), "___ is a way to restrict direct access to some of an object’s fields.")
    ,Question(mutableListOf(Pair("coroutines",true),Pair("ViewModels",false),Pair("returns",false),Pair("managed threads",false)), "To keep the UI running smoothly, use ___ for long-running tasks, such as all database operations.")
    ,Question(mutableListOf(Pair("grid",false),Pair("layout",false),Pair("list",false),Pair("span",true)), "A ___ represents, by default, the items in a row or column when using GridLayout.")
)

class MyViewModel : ViewModel() {

    var playerName : String = ""
    var latestScore = 0F
    private val repository: Repository

    val questions: MutableList<Question> = mutableListOf()
    var categories: MutableList<QuestionCategory> = mutableListOf()
    var questionCounter : Int = 0
    var points : Float = 0F
    var finalPoints : Int = 0

    var myQuizQuestions : MutableLiveData<Response<BaseModel>> = MutableLiveData()
    var mQuizCategories : MutableLiveData<Response<Categories>> = MutableLiveData()

    init {
        repository = Repository()
        getQuestionsForQuiz(20,"multiple",0)
    }

    fun getQuestionsForQuiz(amount: Int, type: String, category: Int){

        viewModelScope.launch() {
            val response = repository.getQuestionsForQuiz(amount,type,category)
            myQuizQuestions.value = response

            Log.d("Response", response.body()?.results.toString())

        }
    }

    fun getCategories(){

        viewModelScope.launch() {
            val response = repository.getCategories()
            mQuizCategories.value = response

            Log.d("Response", response.body()?.triviaCategories.toString())

        }
    }


    fun mapToQuestions(){
        questions.clear()

        myQuizQuestions.value?.body()?.results?.forEach { it ->
            val text = it.question
            val answers = mutableListOf<Pair<String,Boolean>>()
            answers.add(Pair(it.correctAnswer,true))
            it.incorrectAnswers.forEach { it ->
                answers.add(Pair(it,false))
            }

            val question = Question(answers,text)

            questions.add(question)

        }
    }


    fun startQuiz() {
        points = 0F
        finalPoints = 0
        questionCounter = 0

        randomizeQuestions()
    }

    fun typeOfNewxtQuestion() : Int{
        var counter = 0
        for(q in questions[questionCounter].answers!!){
            if(q.second == true){
                counter++
            }
        }
        return counter
    }

    fun randomizeQuestions(){
        questions.shuffle()
        //questions.forEach { q -> q.answers.shuffle() }
    }

    fun endOfQuiz() : Boolean {
        if (questionCounter == questions.size) {
            return true
        }
        return false
    }

    fun getQuestion() : Question{
        val question = Question(questions[questionCounter].answers, questions[questionCounter].text)
        questionCounter++
        return question
    }

    fun calculateResult(question: Question, numbers : MutableList<Int>) {
        finalPoints++

        var correctAnswers = 0F
        for(number in numbers)
            if(question.answers?.get(number)?.second == true){
                correctAnswers++
            }

        var questionCorrectAnswer = 0
        for(q in question.answers!!){
            if(q.second == true)
                questionCorrectAnswer++
        }

        points += correctAnswers/questionCorrectAnswer
    }

}