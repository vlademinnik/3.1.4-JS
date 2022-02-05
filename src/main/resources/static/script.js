const linkDeleteUser = "http://localhost:8080/admin/delete/";
const linkNewUser = "http://localhost:8080/admin/create";
const linkGetAllUsers = "http://localhost:8080/admin/users";
const linkGetUser = "http://localhost:8080/admin/user/";
const linkUpdateUser = "http://localhost:8080/admin/update/";


let userFormNew = {
    firstName: "testing",
    lastName: "1",
    age: 1,
    email: "1",
    password: "1",
    roles: [
        {
            role: "ROLE_ADMIN"
        }
    ]
}

let userForm = {
    id: 2,
    firstName: "testing",
    lastName: "1",
    age: 1,
    email: "1",
    password: "1",
    roles: [
        {
            role: "ROLE_ADMIN"
        }
    ]
}
//---------- Все юзеры и кнопки едит и делет --------------------------------
allUsers();

async function allUsers() {
    console.log("Executing request");
    try {
        const response = await fetch(linkGetAllUsers);
        const users = await response.json();

        if (users.length > 0) {
            let temp = "";
            users.forEach((x) => {
                temp += "<tr><td>" + x.id + "</td>";
                temp += "<td>" + x.firstName + "</td>";
                temp += "<td>" + x.lastName + "</td>";
                temp += "<td>" + x.age + "</td>";
                temp += "<td>" + x.email + "</td>";
                temp += "<td>" + rolesPrint(x.roles)+ "</td>";
                temp += "<td>" + `<button onclick='updateUserForm(${x.id})' type='button' class='btn btn-info'>Edit</button>` + "</td>";
                temp += `<td><button onclick="deletedUser(${x.id})" class=\"btn btn-danger delBtn\">Delete</button></td></tr>`;
            });
            document.getElementById("allUsers").innerHTML = temp;
        }
    } catch (e) {
        console.error(e);
    }
}

function rolesPrint(roles) {
    let print = "";
    for (let i = 0; i < roles.length; i++) {
        print += roles[i].role;
        if (roles.length > 1 && i !== 1) {
            print += ", ";
        }
    }
    return print;
}

//---------- добавление юзера --------------------------------
async function newUser() {
    try {
        await fetch(linkNewUser, {
            method: "POST",
            body: JSON.stringify(userFormNew),
            headers: {
                "Content-Type": "application/json"
            }
        }).then(()=> allUsers());
    } catch (e) {
        console.error(e);
    }
}

newbtn.onclick = function () {
    userFormNew.firstName = $('#new-firstname').val();
    userFormNew.lastName = $('#new-lastname').val();
    userFormNew.age = $('#new-age').val();
    userFormNew.email = $('#new-email').val();
    userFormNew.password = $('#new-password').val();
    userFormNew.roles = $('#new-roles').val();
    newUser().then(allUsers);
}

//---------- редачим юзера --------------------------------
async function updateUserForm(id) {
    $(".editUser #exampleModal").modal();

    let user = await (await fetch(linkGetUser + id)).json();

    $('#id').val(user.id);
    $('#edit-firstname').val(user.firstName);
    $('#edit-lastname').val(user.lastName);
    $('#edit-age').val(user.age);
    $('#edit-email').val(user.email);
    $('#edit-password').val(user.password);
    $('#edit-roles').val(user.roles);

    upbtn.onclick = function (e) {
        e.preventDefault()
        console.log(e)
        userForm.id = id;
        userForm.firstName = $('#edit-firstname').val();
        userForm.lastName = $('#edit-lastname').val();
        userForm.age = $('#edit-age').val();
        userForm.email = $('#edit-email').val();
        userForm.password = $('#edit-password').val();
        userForm.roles = $('#edit-roles').val();
        updateUser(id);
    }
}

async function updateUser(id) {
    try {
        await fetch(linkUpdateUser + id, {
            method: "PUT",
            body: JSON.stringify(userForm),
            headers: {
                "Content-Type": "application/json"
            }
        }).then(()=> allUsers());
    } catch (e) {
        console.error(e);
    }
    // location.reload();
}

//---------- удаление юзера --------------------------------
function deletedUser(id) {
    fetch(linkDeleteUser + id, {
        method: "DELETE",
    }).then(() => allUsers());
}

