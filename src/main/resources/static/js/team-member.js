// Function to add a new team member form
function addExtraTeamMemberForm() {
    const teamMemberForms = document.getElementById('teammember-forms');
    const count = teamMemberForms.getAttribute('data-teammembers-count');
    fetch(`/teams/edit-team-member?index=${count}`)
        .then(response => response.text())
        .then(fragment => {
            teamMemberForms.appendChild(htmlToElement(fragment));
            teamMemberForms.setAttribute('data-teammember-count', parseInt(count) + 1);
        })
        .catch(error => console.error('Error fetching team member form:', error));
}

// Function to remove a team member form by index
function removeTeamMemberForm(formIndex) {
    const teamMemberForm = document.getElementById('teammember-form-section-' + formIndex);
    teamMemberForm
        ? teamMemberForm.parentElement.removeChild(teamMemberForm)
        : console.error('Team member form not found for index:', formIndex);
}

// Function to convert an HTML string to a DOM element
function htmlToElement(html) {
    const template = document.createElement('template');
    html = html.trim();
    template.innerHTML = html;
    return template.content.firstChild;
}