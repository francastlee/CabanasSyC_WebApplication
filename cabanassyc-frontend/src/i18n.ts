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

i18n
  .use(initReactI18next)
  .init({
    resources: {
      en: {
        home: homeEN,
        navbar: navbarEN,
        footer: footerEN,
        cabins: cabinsEN
      },
      es: {
        home: homeES,
        navbar: navbarES,
        footer: footerES,
        cabins: cabinsES
      },
    },
    lng: 'es',
    fallbackLng: 'es',
    defaultNS: 'home', 
    ns: ['home', 'navbar', 'footer', 'cabins'], 
    interpolation: {
      escapeValue: false,
    },
  });

export default i18n;
