/**
 * 
 */


window.onload = function () {
  getCurrentUser();
}

let currentUserId;
//grabbing reimb list 
let reimbHeader = document.getElementById("accordionExample");
//grabing history reimb form
let pastReimbForm = document.getElementById("pastReimbForm");
// pastReimHeader 
let pastReimbHeader = document.getElementById("pastReimbDiv");


function getCurrentUser() {

  let xhttp = new XMLHttpRequest();

  xhttp.onreadystatechange = function () {

    if (xhttp.readyState == 4 && xhttp.status == 200) {
      let loggedUser = JSON.parse(xhttp.responseText);
      currentUserId = loggedUser.userId;
      displayUser(loggedUser);
      getPendingReimbursements();
    }
  }

  xhttp.open("GET", 'http://localhost:9054/ChemX/api/getSession');

  xhttp.setRequestHeader('Content-type', 'application/json');
  xhttp.send();
}

// immediately displays name of user logged in 
function displayUser(user) {
  document.getElementById("greeting").innerText = "Welcome back, " + user.firstName.toUpperCase() + "!";
}

// grabbing all current pending
function getPendingReimbursements() {
  reimbHeader.innerHTML = "";
  fetch("http://localhost:9054/ChemX/api/reimbursements")
    .then(res => res.json())
    .then(res => {
      let reimbursements = res;
      for (let i = 0; i < reimbursements.length; i++) {
        let newReimbSection = `<div class="accordion-item">
      <h2 class="accordion-header" id="${"heading" + reimbursements[i].reimbId}">
        <button class="accordion-button ${i === 0 ? " " : "collapsed"}" type="button" data-bs-toggle="collapse" data-bs-target="#collapse${reimbursements[i].reimbId}" aria-expanded="${i === 0 ? true : false}" aria-controls="${"collapse" + reimbursements[i].reimbId}">
          <pre>#${reimbursements[i].reimbId}       status: ${reimbursements[i].status}</pre>
        </button>
      </h2>
      <div id="${"collapse" + reimbursements[i].reimbId}" class="accordion-collapse collapse ${i === 0 ? "show" : ""}" aria-labelledby="${"heading" + reimbursements[i].reimbId}" data-bs-parent="#accordionExample">
        <div class="accordion-body">
          <p>Submitted by: ${reimbursements[i].author.toUpperCase()}</p>
          <p>Amount: $${reimbursements[i].reimbAmount} </p>
          <p>Description: ${reimbursements[i].description}</p>
          <p>Reimbursement Type: ${reimbursements[i].type}<p>
          <p>Date Submitted: ${sqlToJsDate(reimbursements[i].dateSubmitted)}<p>
      
          <button type="button" name="${reimbursements[i].reimbId}" value="2" class="btn btn-primary" onclick="updateReimbStatus(this.name, this.value)">Approve</button>
          <button type="button" name="${reimbursements[i].reimbId}" value="3" class="btn btn-danger" onclick="updateReimbStatus(this.name, this.value)">Reject</button>
        </div>
      </div>
    </div>`;

        reimbHeader.innerHTML += newReimbSection;
      }

    })
    .catch(e => console.log(e));

}

function sqlToJsDate(sqlDate) {
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

  return new Date(sYear, sMonth, sDay, sHour, sMinute, sSecond, sMillisecond);
}


updateReimbStatus = (id, updateStatus) => {

  let updatedReimbObj = {
    resolver: currentUserId,
    reimbID: id,
    newStatus: updateStatus
  }
  fetch("http://localhost:9054/ChemX/api/reimbursement", {
    method: "PUT",
    body: JSON.stringify(updatedReimbObj),
    headers: {
      "Content-Type": "application/json"
    }
  }).then(res => res.json())
    .then(json => console.log(json));
  setTimeout(() => getPendingReimbursements(), 3000);
}

// form action 

pastReimbForm.addEventListener("submit", (eve) => {
  eve.preventDefault();
  pastReimbHeader.innerHTML = '';
  let reimbStatus = parseInt(pastReimbForm.elements[0].value);
  let employeeID = parseInt(pastReimbForm.elements[1].value);
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
              <p>Submitted by: ${reimbursements[i].author.toUpperCase()}</p>
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