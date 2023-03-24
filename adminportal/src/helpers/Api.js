//This module provides a list of helper methods for accessing the
//Simple CRM RESTful API
const SERVER_PREFIX = "http://localhost:8080/LensLease-war/webresources";
const Api = {
 createUser(data) {
    return fetch(`${SERVER_PREFIX}/users`, {
        headers: {
        Accept: "application/json",
        "Content-Type": "application/json",
        },
        method: "POST",
        body: JSON.stringify(data),
    });
    },

getUser(cId) {
    return fetch(`${SERVER_PREFIX}/users/${cId}`);
    },


getAllUsers() {
    return fetch(`${SERVER_PREFIX}/users`);
    },
   };
   export default Api;