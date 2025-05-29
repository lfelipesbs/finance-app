import currency from 'currency.js';

export const formatMoney = (value) => currency(value, {
    decimal: ',',
    separator: '.',
    precision: 2
}).format({ symbol: ''});
