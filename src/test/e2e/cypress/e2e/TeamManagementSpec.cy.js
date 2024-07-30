describe('Team management', () => {
    beforeEach(() => {
        cy.setCookie('org.springframework.web.servlet.i18n.CookieLocaleResolver.LOCALE', 'en');
        cy.request({
            method: 'POST',
            url: 'api/integration-test/reset-db',
            followRedirect: false
        }).then((response) => {
            expect(response.status).to.eq(200);
        });
    });

    it('should be possible to add a team', () => {
        cy.loginByForm('admin.strator@gmail.com', 'admin-pwd');
        cy.visit('/teams');

        cy.get('#add-team-button').click();
        cy.url().should('include', '/teams/create');
        cy.get('#name').type('Supers');
        cy.get('#leadId').select('Admin Strator');
        cy.get('#submit-button').click();
        cy.get('#teams-table').find('tbody tr').should('have.length', 1);
    });

    it('should not be possible to navigate to the Add Team form', () => {
        cy.loginByForm('user.last@gmail.com', 'user-pwd');
        cy.visit('/teams');
        cy.get('#add-team-button').should('not.exist');
    });

    it('should be possible to delete a team', () => {
        cy.request({
            method: 'POST',
            url: 'api/integration-test/add-test-team',
            followRedirect: false
        }).then((response) => {
            expect(response.status).to.eq(200);
        });
        cy.loginByForm('admin.strator@gmail.com', 'admin-pwd');
        cy.visit('/teams');

        cy.get('#teams-table').find('tbody tr').should('have.length', 1);
        cy.get('[id^=delete-link-]').click();
        cy.get('#delete-modal-message').contains('Are you sure you want to delete team Test Team?');
        cy.get('#delete-modal-submit-button').click();
        cy.get('#teams-table').find('tbody tr').should('have.length', 0);
        cy.get('#success-alert-message').contains('Team Test Team was deleted successfully.');

        cy.reload();
        cy.get('#success-alert-message').should('not.exist');
    });
});