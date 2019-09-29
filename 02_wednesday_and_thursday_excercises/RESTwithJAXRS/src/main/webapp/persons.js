function getPeople() {
    fetch("http://localhost:8080/rest_jaxrs/api/person") //url works
            .then(res => res.json())
            .then(data => {

                let rows = data.map(function (name) {
                    let row = "<tr> <td>" + name.firstName + "</td> <td>" + name.lastName +
                            "</td> <td>" + name.phone + "</td> </tr>";
                    return row;
                })

                let rowsAsString = rows.join("");
                document.getElementById("tbody").innerHTML = rowsAsString;
            })
}

document.getElementById("button").onclick = getPeople;

