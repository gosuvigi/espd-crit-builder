import React, {Component} from 'react';
import {bindActionCreators} from 'redux';
import {connect} from 'react-redux';
import logo from './logo.svg';
import './App.css';
import CriteriaForm from './criterion/CriteriaForm';
import {actions} from './criterion/index';
import Values from './Values';
import 'bootstrap/dist/css/bootstrap.css';

class App extends Component {

    render() {
        const {submitCriteria} = this.props;
        return (
            <div className="App">
                <div className="App-header">
                    <img src={logo} className="App-logo" alt="logo"/>
                    <h2>Welcome to ESPD</h2>
                </div>
                <div>
                    <div>
                        <CriteriaForm onSubmit={submitCriteria}/>
                    </div>
                    <div className="text-left">
                        <Values form="criteriaForm"/>
                    </div>
                </div>
            </div>
        );
    }
}

const mapStateToProps = state => ({});

const mapDispatchToProps = dispatch => bindActionCreators({
    submitCriteria: actions.submitCriteria,
}, dispatch);

App = connect(mapStateToProps, mapDispatchToProps)(App);

export default App;
