describe('Authentication', () => {

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
  
  it('should be possible to log on as a user', () => {
    cy.visit('/login');

    cy.get('#username').type('user.last@gmail.com');
    cy.get('#password').type('user-pwd');
    cy.get('#submit-button').click();

    cy.url().should('include', '/');
  });

  it('should be possible to log on as an admin', () => {
    cy.visit('/login');

    cy.get('#username').type('admin.strator@gmail.com');
    cy.get('#password').type('admin-pwd');
    cy.get('#submit-button').click();

    cy.url().should('include', '/');
  });
});