/**
 * Created by vigi on 5/11/2017 8:37 PM.
 */
import React from 'react';
import {Field, FieldArray, formValueSelector} from 'redux-form';
import {connect} from 'react-redux';

const selector = formValueSelector('criteriaForm');
const renderField = ({input, label, type, meta: {touched, error}, size}) => (
    <div>
        <label>{label}</label>
        <div>
            <input {...input} type={type} placeholder={label} size={size}/>
            {touched && error && <span>{error}</span>}
        </div>
    </div>
);

let ReqResponseTypeSelector = ({input, dispatch}) => (
    <div>
        <label>Response Type</label>
        <div>
            <select {...input} onChange={event => {
                input.onChange(event);
                const value = event.target.value;
                dispatch({type: "CHANGE_RESPONSE_TYPE", payload: value});
            }}>
                <option value="INDICATOR">INDICATOR</option>
                <option value="DESCRIPTION">DESCRIPTION</option>
                <option value="AMOUNT">AMOUNT</option>
                <option value="DATE">DATE</option>
                <option value="NUMBER">NUMBER</option>
                <option value="PERIOD">PERIOD</option>
                <option value="EVIDENCE">EVIDENCE</option>
                <option value="CODE">CODE</option>
            </select>
        </div>
    </div>
);
ReqResponseTypeSelector = connect(
    (state, props) => {
        return {
            responseTypes: selector(state, props.input.name) // connect to requirements array
        }
    }
)(ReqResponseTypeSelector);

const renderSingleRequirement = (requirement, index, fields, identifier) => {
    const newIdentifier = identifier + "." + (index + 1);
    return (
        <div key={index} className="requirement list-group-item list-group-item-success">
            <div className="row">
                <div className="col-md-3">
                    <Field
                        name={`${requirement}.description`}
                        type="text"
                        component={renderField}
                        label={`Req #${newIdentifier} description`}
                    />
                </div>
                <div className="col-md-3">
                    <Field
                        name={`${requirement}.id`}
                        type="text"
                        component={renderField}
                        label="ID"
                    />
                </div>
                <div className="col-md-2">
                    <div>
                        {/*<Field*/}
                        {/*name={`${requirement}.responseType`}*/}
                        {/*component="select"*/}
                        {/*label="Requirement Type"*/}
                        {/*>*/}
                        {/*<option value="INDICATOR">INDICATOR</option>*/}
                        {/*<option value="DESCRIPTION">DESCRIPTION</option>*/}
                        {/*<option value="AMOUNT">AMOUNT</option>*/}
                        {/*<option value="DATE">DATE</option>*/}
                        {/*<option value="NUMBER">NUMBER</option>*/}
                        {/*<option value="PERIOD">PERIOD</option>*/}
                        {/*<option value="EVIDENCE">EVIDENCE</option>*/}
                        {/*</Field>*/}
                        <Field name={`${requirement}.responseType`}
                               component={ReqResponseTypeSelector}
                               array={`${requirement}.responseTypes`}/>
                    </div>
                </div>
                <div className="col-md-3">
                    <Field
                        name={`${requirement}.value`}
                        type="text"
                        component={renderField}
                        label="Value"
                    />
                </div>
                <div className="col-md-1">
                    <button
                        type="button" className="btn btn-success glyphicon glyphicon-trash"
                        title="Remove Requirement"
                        onClick={() => fields.remove(index)}
                    />
                </div>
            </div>
        </div>
    )
};

const renderRequirements = ({fields, meta: {error}, identifier, nesting}) => (
    <div className="requirements list-group text-left">
        {
            fields.map((requirement, index, fields) => renderSingleRequirement(requirement, index, fields, identifier))
        }
        {error && <li className="error">{error}</li>}
        <div>
            <button type="button" onClick={() => fields.push({responseType: 'INDICATOR'})}>Add Requirement</button>
        </div>
    </div>
);

const renderSingleRequirementGroup = (reqGroup, index, fields, identifier, nesting) => {
    const newIdentifier = !identifier ? (index + 1) : identifier + "." + (index + 1);
    return (
        <div key={index} className="requirement-group list-group-item list-group-item-warning text-left">
            <div className="row">
                <div className="col-md-3">
                    <h4>Requirement group #{newIdentifier}</h4>
                </div>
                <div className="col-md-3">
                    <Field
                        name={`${reqGroup}.name`}
                        type="text"
                        component={renderField}
                        label="Group Name"
                        className="text-left"
                    />
                </div>
                <div className="col-md-3">
                    <Field
                        name={`${reqGroup}.id`}
                        type="text"
                        component={renderField}
                        label="ID"
                    />
                </div>
                <div className="col-md-1">
                    <button
                        type="button" className="btn btn-danger glyphicon glyphicon-trash"
                        title="Remove Requirement Group"
                        onClick={() => fields.remove(index)}
                    />
                </div>
            </div>
            <div className="row">


                <FieldArray name={`${reqGroup}.requirements`} component={renderRequirements}
                            identifier={newIdentifier}
                            nesting={nesting + 1}/>
                <FieldArray name={`${reqGroup}.subgroups`} component={renderRequirementGroups}
                            identifier={newIdentifier} nesting={nesting + 1}/>
            </div>
        </div>
    )
};

const renderRequirementGroups = ({fields, meta: {error, submitFailed}, identifier, nesting}) => {
    const newName = !identifier ? "G" + (fields.length + 1) : "G" + identifier + "." + (fields.length + 1);
    return (
        <div className="row">
            <div className={`col-md-11 col-md-offset-1`}>
                <div className="requirement-groups list-group text-left">
                    {
                        fields.map((reqGroup, index, fields) => (renderSingleRequirementGroup(reqGroup, index, fields, identifier, nesting)))
                    }
                    <div>
                        <button type="button" onClick={() => fields.push({name: newName})}>Add Requirement Group
                        </button>
                        {submitFailed && error && <span>{error}</span>}
                    </div>
                </div>
            </div>
        </div>
    )
};

const EditCriterion = ({fields}) => {
    return (
        <div className="criterion col-md-12 bg-info">
            <div className="row">
                <div className="col-md-6 text-left">
                    <Field
                        name="name"
                        type="text"
                        component={renderField}
                        label="Criterion Name"
                        size="100"
                    />
                </div>
                <div className="col-md-6 text-left">
                    <Field
                        name="uuid"
                        type="text"
                        component={renderField}
                        label="UUID"
                        size="100"
                    />
                </div>
                <div className="col-md-6 text-left">
                    <label>Criterion Description</label>
                    <div>
                        <Field
                            name="description"
                            component="textarea"
                            cols="100"
                        />
                    </div>
                </div>
                <div className="col-md-11 col-md-offset-1">
                    <FieldArray name="groups" component={renderRequirementGroups} identifier="" nesting={1}/>
                </div>
            </div>
        </div>
    )
};

export default EditCriterion;
