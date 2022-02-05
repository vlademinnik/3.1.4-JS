(async function () {
    const res = await fetch('http://localhost:8080/authorized')
    const user = await res.json()
    const userInfo = document.getElementById("user_v_baze")
    if(Object.keys(user).length === 0){
        return
    }
    let inf = `<tr>
        <td>${user.id}</td>
        <td>${user.firstName}</td>
        <td>${user.lastName}</td>
        <td>${user.age}</td>
        <td>${user.email}</td>
        <td>${user.roles[0]?.role}</td>
        </tr>`
    userInfo.innerHTML = inf
})()