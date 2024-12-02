<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Cadastro de Produto</title>
</head>
<body>
    <h2>Formulário de Cadastro de Produto</h2>
    <form action="salvarProduto" method="post">
        <label for="nome">Nome:</label>
        <input type="text" id="nome" name="nome" value="${not empty produto ? produto.nome : ''}" required/><br/>

        <label for="preco">Preço:</label>
        <input type="number" id="preco" name="preco" value="${not empty produto ? produto.preco : ''}" required/><br/>

        <input type="submit" value="Salvar Produto"/>
    </form>
</body>
</html>