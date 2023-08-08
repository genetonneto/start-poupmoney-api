async function createExpenseData() {
    let nome = document.getElementById("nome")
    let description = document.getElementById("description")
    let tipo = document.getElementById("tipo")
    let dataCriacao = document.getElementById("dataCriacao")
    let valor = document.getElementById("valor")

    await fetch("http://localhost:8080/expense", {
        method: "POST",
        headers: new Headers({
          "Content-Type": "application/json; charset=utf8",
          Accept: "application/json",
        }),
        body: JSON.stringify({
            nome,
            description,
            tipo,
            dataCriacao,
            valor
        }),
      });

      window.setTimeout(function () {
        window.location = "/poupmoney/view/loading.html";
      }, 2000);
}