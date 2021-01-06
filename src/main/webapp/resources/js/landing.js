
window.onload = function () {
  getCurrentUser();
}

let currentUserId;

function getCurrentUser() {

  let xhttp = new XMLHttpRequest();

  xhttp.onreadystatechange = function () {

    if (xhttp.readyState == 4 && xhttp.status == 200) {
      let loggedUser = JSON.parse(xhttp.responseText);
      currentUserId = loggedUser.userId;
      displayUser(loggedUser);
      getUsersReimbursements(currentUserId);


    }
  }

  xhttp.open("GET", 'http://localhost:9054/ChemX/api/getSession');

  xhttp.setRequestHeader('Content-type', 'application/json');
  xhttp.send();

}



// immediately displays name of user logged in 
function displayUser(user) {
  document.getElementById("greeting").innerText = `Welcome back, ${user.firstName.toUpperCase()}!`;

}

// grabbing form 
let inputForm = document.getElementById("newReimbursementForm");
//grabbing reimb list
let reimbHeader = document.getElementById("accordionExample");
//past reimb list 
let pastReimbHeader = document.getElementById("pastReimbDiv");

// form values
const amount = inputForm.elements[0];
const reimbType = inputForm.elements[1];
const description = inputForm.elements[2];
const requiredFields = [
  { input: amount, message: "Amount in dollars and cents required" },
  { input: reimbType, message: "Reimbursement category selection required" },
  { input: description, message: "Description of expense required" }
];

//form validation 
function inputError(input, message) {
  input.classNAme = "error";
  //should grab small 
  const error = input.previousElementSibling;
  error.innerText = message;
  return false;
}

function inputSuccess(input) {
  input.classNAme = "success";

  const error = input.previousElementSibling;
  error.innerText = '';
  return true;
}

function requiredValue(input, message) {
  return input.value.trim() === "" ?
    inputError(input, message) :
    inputSuccess(input);
}

function validateAmount(input) {
  const re = /^-?(?:0|[1-9]\d{0,2}(?:,?\d{3})*)(?:\.\d+)?$/;

  return re.test(input.value.trim()) ?
    inputSuccess(input) :
    inputError(input, 'Invalid money format');
}

// form action 
inputForm.addEventListener("submit", (eve) => {
  eve.preventDefault();
  let valid = true;
  let reimbursementObj;
  // checks that nothing is empty 
  requiredFields.forEach((input) => {
    valid = requiredValue(input.input, input.message);
  });

  if (valid) {
    valid = validateAmount(amount);

    reimbursementObj = {
      userId: currentUserId,
      amount: amount.value,
      type: reimbType.value,
      desc: description.value
    }

    fetch("http://localhost:9054/ChemX/api/reimbursement",
      {
        method: "post",
        headers: {
          "Content-Type": "application/json"
        },
        body: JSON.stringify(reimbursementObj)
      })
      .then(res => res.json())
      .then(res => {
        console.log(res)

      });
      }

  if (!valid) {
    console.log("invalid format")
  }
  inputForm.reset();
  getUsersReimbursements();
});


// grabbing all current reimbursments
function getUsersReimbursements() {
  reimbHeader.innerHTML = "";

  fetch("http://localhost:9054/ChemX/api/reimbursement?user=" + currentUserId +"&status=1")
  .then(res=> res.json())
  .then(res => {
    let reimbursements = res; 

    for (let i = 0; i < reimbursements.length; i++) {
      let newReimbSection = `<div class="accordion-item">
      <h2 class="accordion-header" id="${"heading" + reimbursements[i].reimbId}">
        <button class="accordion-button ${i === 0 ? " ": "collapsed"}" type="button" data-bs-toggle="collapse" data-bs-target="#collapse${reimbursements[i].reimbId}" aria-expanded="${i === 0 ? true: false}" aria-controls="${"collapse" + reimbursements[i].reimbId}">
        <pre>#${reimbursements[i].reimbId}       status: ${reimbursements[i].status}</pre>
        </button>
      </h2>
      <div id="${"collapse" + reimbursements[i].reimbId}" class="accordion-collapse collapse ${i === 0 ? "show": ""}" aria-labelledby="${"heading" + reimbursements[i].reimbId}" data-bs-parent="#accordionExample">
        <div class="accordion-body">
          <p>Amount: $${reimbursements[i].reimbAmount} </p>
          <p>Description: ${reimbursements[i].description}</p>
          <p>Date Submitted: ${sqlToJsDate(reimbursements[i].dateSubmitted)}<p>
          <p>Reimbursement Type: ${reimbursements[i].type}<p>
         <p> Approved by: ${reimbursements[i].dateResolved ? reimbursements[i].resolver : "--"}</p>
          <p>Approved on: ${reimbursements[i].dateResolved ? reimbursements[i].dateResolved : "--"}</p>
        </div>
      </div>
    </div>`;

    reimbHeader.innerHTML += newReimbSection;
    }

  })
  .catch(e => console.log(e));

}


function sqlToJsDate(sqlDate){
  //sqlDate in SQL DATETIME format ("yyyy-mm-dd hh:mm:ss.ms")
  var sqlDateArr1 = sqlDate.split("-");
  //format of sqlDateArr1[] = ['yyyy','mm','dd hh:mm:ms']
  var sYear = sqlDateArr1[0];
  var sMonth = (Number(sqlDateArr1[1]) - 1).toString();
  var sqlDateArr2 = sqlDateArr1[2].split(" ");
  //format of sqlDateArr2[] = ['dd', 'hh:mm:ss.ms']
  var sDay = sqlDateArr2[0];
  var sqlDateArr3 = sqlDateArr2[1].split(":");
  //format of sqlDateArr3[] = ['hh','mm','ss.ms']
  var sHour = sqlDateArr3[0];
  var sMinute = sqlDateArr3[1];
  var sqlDateArr4 = sqlDateArr3[2].split(".");
  //format of sqlDateArr4[] = ['ss','ms']
  var sSecond = sqlDateArr4[0];
  var sMillisecond = sqlDateArr4[1];
  
  return new Date(sYear,sMonth,sDay,sHour,sMinute,sSecond,sMillisecond);
}

pastReimbForm.addEventListener("submit", (eve) => {
  eve.preventDefault();
  pastReimbHeader.innerHTML = '';
  let reimbStatus = parseInt(pastReimbForm.elements[0].value);
  let employeeID = currentUserId;
  if (!isNaN(reimbStatus) & !isNaN(employeeID)) {
    // fetch
    fetch("http://localhost:9054/ChemX/api/reimbursement?user=" + employeeID + "&status=" + reimbStatus)
      .then(res => res.json())
      .then(res => {
        let reimbursements = res;
        let newReimbSection;
        if (res.length !== 0) {
          for (let i = 0; i < reimbursements.length; i++) {
            newReimbSection = `<div class="accordion-item">
            <h2 class="accordion-header" id="${"heading" + reimbursements[i].reimbId}">
              <button class="accordion-button ${i === 0 ? " " : "collapsed"}" type="button" data-bs-toggle="collapse" data-bs-target="#collapse${reimbursements[i].reimbId}" aria-expanded="${i === 0 ? true : false}" aria-controls="${"collapse" + reimbursements[i].reimbId}">
               <pre>#${reimbursements[i].reimbId}       status: ${reimbursements[i].status}</pre>
              </button>
            </h2>
            <div id="${"collapse" + reimbursements[i].reimbId}" class="accordion-collapse collapse ${i === 0 ? "show" : ""}" aria-labelledby="${"heading" + reimbursements[i].reimbId}" data-bs-parent="#pastReimbDiv">
            <div class="accordion-body">
              <p>Amount: $${reimbursements[i].reimbAmount} </p>
              <p>Description: ${reimbursements[i].description}</p>
              <p>Date Submitted: ${sqlToJsDate(reimbursements[i].dateSubmitted)}<p>
              <p>Reimbursement Type: ${reimbursements[i].type}<p>
              <p>Decided by: ${reimbursements[i].dateResolved ? reimbursements[i].resolver : "--"}</p>
              <p>Decided on: ${reimbursements[i].dateResolved ? sqlToJsDate(reimbursements[i].dateResolved): "--"}</p>
            </div>
            </div>
            </div>`;

            pastReimbHeader.innerHTML += newReimbSection

          }
        } else {
          newReimbSection = `<h3>No reimbursements to show</h3>`
          pastReimbHeader.innerHTML = newReimbSection
          setTimeout(() => {pastReimbHeader.innerHTML = ''}, 3000)
        }


      })
      .catch(e => console.log(e));

  } else {
    document.getElementById("invalidInput").innerHTML = `<p> please select a status and employee`;
    setTimeout(() => { document.getElementById("invalidInput").innerHTML = '' }, 3000);

  }
  pastReimbForm.reset();
});
