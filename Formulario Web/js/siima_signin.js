
window.addEventListener("load",main,false);

let input_first_name;
let input_second_name;
let input_sur_name;
let input_age;
let input_genere;
let input_phone;

function main(){
    console.log("Script started");
    
    // get inputs of DOM
    input_first_name = document.getElementsByName("first_name");
    input_second_name = document.getElementsByName("second_name");
    input_sur_name = document.getElementsByName("sur_name");
    input_age = document.getElementsByName("age");
    input_genere = document.getElementsByName("genere");
    input_phone = document.getElementsByName("phone");
    
}