var myFirebaseRef = new Firebase("https://easyclick.firebaseio.com/");


var shown = true;
$("#newPost").hide()
var show = document.getElementById('buttonShow');

var ref = myFirebaseRef.child("Answers")
ref.on("child_added", function(snapshot) {
  var newPost = snapshot.val();
});
    posts = newPost + newPost;
    $(".results").append("Answers:" + newPost + " <br>");


  $("#buttonShow").click(function(){
  if (shown == false) {
    $("#newPost").hide(500)
    shown = true;
  }else if (shown == true) {
    $("#newPost").show(500)
    shown = false;
  }; 
  })

function newPost(questionTitle, question, A, B, C, D, E){
  var Questions = myFirebaseRef.child("Questions");
Questions.child(questionTitle).set({
  Question: question,
  AnswerA: A,
  AnswerB: B,
  AnswerC: C,
  AnswerD: D,
  AnswerE: E,
})
//inputing user answers::BUILDING ANSWER DATABASE:
  var username = "CorrectAnswer"
  var Answer = Questions.child(questionTitle);
  var answerResponse = {};
  answerResponse[username] = "null";
Answer.child("Answers").set(answerResponse);

var correct;

 if ($('#A_correct').is(':checked')) {
  answerResponse["A"] = "True";
  Answer.child("CorrectAnswers").set(answerResponse);
  correct = " A";
  };
 if ($('#B_correct').is(':checked')) {
  answerResponse["B"] = "True";
  Answer.child("CorrectAnswers").set(answerResponse);
  correct = correct + " B";
  };
 if ($('#C_correct').is(':checked')) {
  answerResponse["C"] = "True";
  Answer.child("CorrectAnswers").set(answerResponse);
  correct = correct + " C";
  };
 if ($('#D_correct').is(':checked')) {
  answerResponse["D"] = "True";
  Answer.child("CorrectAnswers").set(answerResponse);
  correct = correct + " D";
  };
 if ($('#E_correct').is(':checked')) {
  answerResponse["E"] = "True";
  Answer.child("CorrectAnswers").set(answerResponse);
  correct = correct + " E";
  };

//clear boxes after submission
$('.answer').val('');
$('#questionTitle').val('');
$('#question').val('');
$('input:checkbox').removeAttr('checked');


  $(".results").append("Question Title:" + questionTitle + " <br>");
  $(".results").append("Question:" + question + " <br>");
  $(".results").append("A:" + A + " <br>");
  $(".results").append("B:" + B + " <br>");
  $(".results").append("C:" + C + " <br>");
  $(".results").append("D:" + D + " <br>");
  $(".results").append("E:" + E + " <br>");
  $(".results").append("Correct Answer(s):" + correct + " <br>");

//get new question values

}

function submitPost(newQuestion){
  console.log(newQuestion);
myFirebaseRef.push({
  Question: newQuestion 
});
}
