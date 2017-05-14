/**
 * Created by vigi on 5/11/2017 8:38 PM.
 */
import React from 'react';
import {connect} from 'react-redux';
import {reduxForm, FieldArray} from 'redux-form';
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

CriteriaForm = reduxForm({
    form: 'criteriaForm', // a unique identifier for this form,
    destroyOnUnmount: false,        // <------ preserve form data
    enableReinitialize: true,       // <------ permits to reinitialize the initial values
    keepDirtyOnReinitialize: true,   // <------ keep the values added/changed by the user
})(CriteriaForm);

CriteriaForm = connect(
    state => ({
        initialValues: state.criteria // pull initial values from criteria reducer
    }),
    // {load: loadCriteria} // bind criteria loading action creator
)(CriteriaForm);

export default CriteriaForm;

