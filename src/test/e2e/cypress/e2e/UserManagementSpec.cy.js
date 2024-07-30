describe('User management', () => {
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

  it('should be possible to navigate to the Add User form', () => {
    cy.loginByForm('admin.strator@gmail.com', 'admin-pwd');
    cy.visit('/users');
    cy.get('#add-user-button').click();

    cy.url().should('include', '/users/create');
  });

  it('should not be possible to navigate to the Add User form', () => {
    cy.loginByForm('user.last@gmail.com', 'user-pwd');
    cy.visit('/users');
    cy.get('#add-user-button').should('not.exist');
  });
});