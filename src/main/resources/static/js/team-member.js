function addExtraTeamMemberForm() { // <.>
    const teamMemberForms = document.getElementById('teammember-forms'); // <.>
    const count = teamMemberForms.getAttribute('data-teammembers-count'); // <.>
    fetch(`/teams/edit-team-member?index=${count}`) // <.>
        .then(response => response.text()) // <.>
        .then(fragment => {
            teamMemberForms.appendChild(htmlToElement(fragment)); // <.>
            teamMemberForms.setAttribute('data-teammember-count', parseInt(count) + 1); // <.>
        });
}

function removeTeamMemberForm(formIndex) {
    const teammemberForm = document.getElementById('teammember-form-section-' + formIndex);
    teammemberForm.parentElement.removeChild(teammemberForm);
}

function htmlToElement(html) {
    const template = document.createElement('template');
    html = html.trim(); // Never return a text node of whitespace as the result
    template.innerHTML = html;
    return template.content.firstChild;
}