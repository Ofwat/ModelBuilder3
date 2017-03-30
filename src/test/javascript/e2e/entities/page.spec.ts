import { browser, element, by, $ } from 'protractor';

describe('Page e2e test', () => {

    const username = element(by.id('username'));
    const password = element(by.id('password'));
    const entityMenu = element(by.id('entity-menu'));
    const accountMenu = element(by.id('account-menu'));
    const login = element(by.id('login'));
    const logout = element(by.id('logout'));

    beforeAll(() => {
        browser.get('/');

        accountMenu.click();
        login.click();

        username.sendKeys('admin');
        password.sendKeys('admin');
        element(by.css('button[type=submit]')).click();
        browser.waitForAngular();
    });

    it('should load Pages', () => {
        entityMenu.click();
        element.all(by.css('[routerLink="page"]')).first().click().then(() => {
            const expectVal = /Pages/;
            element.all(by.css('h2 span')).first().getText().then((value) => {
                expect(value).toMatch(expectVal);
            });
        });
    });

    it('should load create Page dialog', function () {
        element(by.css('button.create-page')).click().then(() => {
            const expectVal = /Create or edit a Page/;
            element.all(by.css('h4.modal-title')).first().getText().then((value) => {
                expect(value).toMatch(expectVal);
            });

            element(by.css('button.close')).click();
        });
    });

    afterAll(function () {
        accountMenu.click();
        logout.click();
    });
});
