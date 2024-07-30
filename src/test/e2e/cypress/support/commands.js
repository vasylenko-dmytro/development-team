Cypress.Commands.add('loginByForm', (username, password) => {
    Cypress.log({
        name: 'loginByForm',
        message: `${username} | ${password}`,
    });

    return cy.request('/login')
        .its('body')
        .then((body) => {
            const $html = Cypress.$(body);
            const csrf = $html.find('input[name=_csrf]').val();

            cy.loginByCSRF(username, password, csrf)
                .then((resp) => {
                    expect(resp.status).to.eq(200);
                });
        });
});

Cypress.Commands.add('loginByCSRF', (username, password, csrfToken) => {
    cy.request({
        method: 'POST',
        url: '/login',
        failOnStatusCode: false,
        form: true,
        body: {
            username,
            password,
            _csrf: csrfToken
        }
    });
});