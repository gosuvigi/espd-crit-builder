/**
 * Created by vigi on 5/11/2017 8:38 PM.
 */
import React from 'react';
import {bindActionCreators} from 'redux';
import {connect} from 'react-redux';
import {reduxForm} from 'redux-form';
import EditCriterion from './EditCriterion';

let CriteriaForm = props => {
    const {handleSubmit, pristine, reset, submitting} = props;
    return (
        <form onSubmit={handleSubmit} className="criteriaForm">
            <div className="container-fluid">
                <div className="row">
                    <EditCriterion/>
                </div>
                <div className="row">
                    <button type="submit" disabled={submitting} className="btn btn-primary">Submit</button>
                    <button type="button" disabled={pristine || submitting} onClick={reset} className="btn btn-default">
                        Clear Values
                    </button>
                </div>
            </div>
        </form>
    )
};

export default reduxForm({
    form: 'criteriaForm', // a unique identifier for this form,
    destroyOnUnmount: false,        // <------ preserve form data
    enableReinitialize: true,       // <------ permits to reinitialize the initial values
    keepDirtyOnReinitialize: true,   // <------ keep the values added/changed by the user
})(CriteriaForm);

