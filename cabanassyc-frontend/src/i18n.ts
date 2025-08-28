import i18n from 'i18next';
import { initReactI18next } from 'react-i18next';

import homeEN from './i18n/locales/en/home.json';
import homeES from './i18n/locales/es/home.json';

import navbarEN from './i18n/locales/en/navbar.json';
import navbarES from './i18n/locales/es/navbar.json';

import footerEN from './i18n/locales/en/footer.json';
import footerES from './i18n/locales/es/footer.json';

import cabinsEN from './i18n/locales/en/cabins.json';
import cabinsES from './i18n/locales/es/cabins.json';

import contactEN from './i18n/locales/en/contact.json';
import contactES from './i18n/locales/es/contact.json';

import toursEN from './i18n/locales/en/tours.json';
import toursES from './i18n/locales/es/tours.json';

import loginEN from './i18n/locales/en/login.json';
import loginES from './i18n/locales/es/login.json';

import registerEN from './i18n/locales/en/register.json';
import registerES from './i18n/locales/es/register.json';

import errorsEN from './i18n/locales/en/errors.json';
import errorES from './i18n/locales/es/errors.json';

i18n
  .use(initReactI18next)
  .init({
    resources: {
      en: {
        home: homeEN,
        navbar: navbarEN,
        footer: footerEN,
        cabins: cabinsEN,
        contact: contactEN,
        tours: toursEN,
        login: loginEN,
        register: registerEN,
        errors: errorsEN
      },
      es: {
        home: homeES,
        navbar: navbarES,
        footer: footerES,
        cabins: cabinsES,
        contact: contactES,
        tours: toursES,
        login: loginES,
        register: registerES,
        errors: errorES
      },
    },
    lng: 'es',
    fallbackLng: 'es',
    defaultNS: 'home', 
    ns: ['home', 'navbar', 'footer', 'cabins', 'contact', 'tours', 'register', 'login', 'errors'], 
    interpolation: {
      escapeValue: false,
    },
  });

export default i18n;
