(async function () {
    const res = await fetch('http://localhost:8080/admin/user')
    const user = await res.json()
    const userInfo = document.getElementById("admin_v_baze")
    if(Object.keys(user).length === 0){
        return
    }
    let data =`<tr>
        <td>${user.id}</td>
        <td>${user.firstName}</td>
        <td>${user.lastName}</td>
        <td>${user.age}</td>
        <td>${user.email}</td>
        <td>${user.roles[0]?.role}</td>
            </tr>`
    userInfo.innerHTML = data
})()