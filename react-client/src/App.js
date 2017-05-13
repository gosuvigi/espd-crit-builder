import React, {Component} from 'react';
import logo from './logo.svg';
import './App.css';
import CriteriaForm from './CriteriaForm';
import showResults from './showResults';
import {Values} from 'redux-form-website-template';
import 'bootstrap/dist/css/bootstrap.css';

class App extends Component {
    render() {
        return (
            <div className="App">
                <div className="App-header">
                    <img src={logo} className="App-logo" alt="logo"/>
                    <h2>Welcome to ESPD</h2>
                </div>
                <div>
                    <div>
                        <CriteriaForm onSubmit={showResults}/>
                    </div>
                    <div>
                        <Values form="criteriaForm"/>
                    </div>
                </div>
            </div>
        );
    }
}

export default App;
