/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


function toggle_visibility(id) {
    var e = document.getElementById(id);
    if (e.style.display == 'inline-table')
        e.style.display = 'none';
    else
        e.style.display = 'inline-table';
}

function toggle_session_visibility(id) {
    var e = document.getElementById(id);
    var d = document.getElementById("DurationChart");
    if (e.style.visibility == 'visible')
        e.style.visibility = 'hidden';
    else
        e.style.visibility = 'visible';

        
}


function onSignIn(googleUser) {
    var profile = googleUser.getBasicProfile();
    var id_token = googleUser.getAuthResponse().id_token;
    console.log('ID: ' + profile.getId()); // Do not send to your backend! Use an ID token instead.
    console.log('Name: ' + profile.getName());
    console.log('Image URL: ' + profile.getImageUrl());
    console.log('Email: ' + profile.getEmail());
    toggle_visibility(signOut);

}

function view_analysis(id, ie) {
    var e = document.getElementById(id);
    var d = document.getElementById(ie);
    if (e.style.display == 'none')
        e.style.display = 'inline-table';
    d.style.display = 'none';
}

function view_dashboard(id, ie) {
    var e = document.getElementById(id);
    var d = document.getElementById(ie);
    if (e.style.display == 'none')
        e.style.display = 'inline-table';
    d.style.display = 'none';

}



           