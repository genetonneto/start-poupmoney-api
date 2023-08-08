const expensesEndpoint = "http://localhost:8080/expense/user";

function hideLoader() {
  document.getElementById("loading").style.display = "none";
}

function show(expenses) {
  let tab = `<thead>
            <th scope="col">Id</th>
            <th scope="col">Nome</th>
            <th scope="col">Descrição</th>
            <th scope="col">Tipo</th>
            <th scope="col">Valor</th>
        </thead>`;

  for (let expense of expenses) {
    tab += `
            <tr>
                <td scope="row">${expense.id}</td>
                <td>${expense.nome}</td>
                <td>${expense.description}</td>
                <td>${expense.tipo}</td>
                <td>${expense.valor}</td>
            </tr>
        `;
  }

  document.getElementById("expenses").innerHTML = tab;
}

async function getExpenses() {
  let key = "Authorization";
  const response = await fetch(expensesEndpoint, {
    method: "GET",
    headers: new Headers({
      Authorization: localStorage.getItem(key),
    }),
  });

  var data = await response.json();
  console.log(data);
  if (response) hideLoader();
  show(data);
}

document.addEventListener("DOMContentLoaded", function (event) {
  if (!localStorage.getItem("Authorization"))
    window.location = "/view/login.html";
});

getExpenses();