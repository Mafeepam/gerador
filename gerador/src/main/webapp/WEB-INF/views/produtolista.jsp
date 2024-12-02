<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Lista de Produtos</title>
</head>
<body>
    <h2>Produtos</h2>
    <a href="adicionarProdutos">Adicionar Novo Produto</a><br/><br/>

    <c:if test="${not empty produtos}">
        <table border="1">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Nome</th>
                    <th>Preço</th>
                    <th>Anotações</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="produto" items="${produtos}">
                    <tr>
                        <td>${produto.id}</td>
                        <td>${produto.nome}</td>
                        <td>${produto.preco}</td>
                        <td>
                            <a href="editarProdutos?id=${produto.id}">Editar</a>
                            <a href="excluirProdutos?id=${produto.id}" onclick="return confirm('Tem certeza que deseja excluir este produto?');">Excluir</a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </c:if>
    <c:if test="${empty produtos}">
        <p>Nenhum produto encontrado.</p>
    </c:if>
</body>
</html>