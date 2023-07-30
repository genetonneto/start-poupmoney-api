const url = "http://localhost:8080/expense/user/1";

function hideLoader() {
  document.getElementById("loading").style.display = "none";
}

function formatCurrency(value) {
  return new Intl.NumberFormat("pt-BR", {
    style: "currency",
    currency: "BRL",
  }).format(value);
}

function show(expenses) {
  let tab = '';

  for (let expense of expenses) {
    tab += `
        <tr>
            <td scope="row">${expense.id}</td>
            <td>${expense.nome}</td>
            <td>${expense.description}</td>
            <td>${expense.tipoDescricao}</td>
            <td>${formatCurrency(expense.valor)}</td>
            <td>${expense.dataRegistroDespesa}</td>
        </tr>
        `;
  }

  document.getElementById("expenses").innerHTML = tab;
}

async function getAPI(url) {
  try {
    const response = await fetch(url, { method: "GET" });

    if (!response.ok) {
      throw new Error("Erro ao obter as despesas.");
    }

    const data = await response.json();
    console.log(data);
    show(data);
  } catch (error) {
    console.error(error.message);
  } finally {
    hideLoader();
  }
}

async function getBudgetAPI() {
  try {
    const response = await fetch("http://localhost:8080/user/1", { method: "GET" });

    if (!response.ok) {
      throw new Error("Erro ao obter o orçamento do usuário.");
    }

    const user = await response.json();
    console.log(user);

    // Exiba o valor do orçamento no container budget
    const budgetContainer = document.getElementById("budget-container");
    budgetContainer.innerHTML = `<p>Total: ${formatCurrency(user.budget)}</p>`;
  } catch (error) {
    console.error(error.message);
  }
}

getAPI(url);
getBudgetAPI(); 