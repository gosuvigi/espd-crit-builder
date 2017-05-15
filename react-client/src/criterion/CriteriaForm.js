/**
 * Created by vigi on 5/11/2017 8:38 PM.
 */
import React from 'react';
import {bindActionCreators} from 'redux';
import {connect} from 'react-redux';
import {reduxForm, Field, formValueSelector} from 'redux-form';
import EditCriterion from './EditCriterion';
import {actions} from './index';

let CriteriaForm = props => {
    const {handleSubmit, pristine, reset, submitting} = props;
    return (
        <form onSubmit={handleSubmit} className="criteriaForm">
            <div className="col-md-10 text-left">
                <label>Paste Criterion JSON data here</label>
                <div>
                    <Field
                        name="pastedJson"
                        component="textarea"
                        cols="150"
                    />
                </div>
            </div>
            <div className="col-md-1">
                <button type="button" onClick={() => props.loadPastedJson(props.pastedJsonValue)}>Load Criterion</button>
            </div>
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

const selector = formValueSelector('criteriaForm');

const mapStateToProps = state => ({
    initialValues: state.criteria.pastedJson, // pull initial values from criteria reducer
    pastedJsonValue: selector(state, 'pastedJson')
});

const mapDispatchToProps = dispatch => bindActionCreators({
    loadPastedJson: actions.loadPastedJson
}, dispatch);

CriteriaForm = connect(mapStateToProps, mapDispatchToProps)(CriteriaForm);

export default CriteriaForm;

