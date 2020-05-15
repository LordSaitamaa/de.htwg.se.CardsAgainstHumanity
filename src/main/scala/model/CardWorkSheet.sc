
import model._
import scala.collection.mutable.ListBuffer

val testStandard = new StandardCards
println(testStandard.getQuestionCards)
println(testStandard.getStandardAnswer)

val testAdd = new KompositumCard
testAdd.addNewCard(new AnswerCard("Hello",true))
testAdd.addNewCard(new QuestionCard("Am i good ?", true))

val x = List[AnswerCard]()
//x += testStandard.getStandardAnswer.foreach(new AnswerCard(_,true))
for(e <- testStandard.getStandardAnswer ){
  x + = new AnswerCard(e,true)
}

println(x)