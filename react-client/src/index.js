import React from 'react';
import {render} from 'react-dom';
import App from './App';
import './index.css';
import {Provider} from 'react-redux';
import {applyMiddleware, createStore, compose} from 'redux';
import rootReducer from './rootReducer';
import thunk from 'redux-thunk';

const store = createStore(
    rootReducer, {},
    compose(
        applyMiddleware(thunk),
        window.devToolsExtension ? window.devToolsExtension() : f => f
    )
);

render(
    <Provider store={store}>
        <App />
    </Provider>,
    document.getElementById('root')
);
