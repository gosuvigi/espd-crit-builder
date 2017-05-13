/**
 * Created by vigi on 5/11/2017 8:37 PM.
 */
import React from 'react';
import {Field, FieldArray} from 'redux-form';

const renderField = ({input, label, type, meta: {touched, error}}) => (
    <div>
        <label>{label}</label>
        <div>
            <input {...input} type={type} placeholder={label}/>
            {touched && error && <span>{error}</span>}
        </div>
    </div>
);

const renderSingleRequirement = (requirement, index, fields, identifier, nesting) => {
    const newIdentifier = identifier + "." + (index + 1);
    return (
        <div key={index} className="requirement list-group-item list-group-item-success">
            <div className="row">
                <div className="col-md-3">
                    <Field
                        name={`${requirement}.name`}
                        type="text"
                        component={renderField}
                        label={`Requirement #${newIdentifier}`}
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
            fields.map((requirement, index, fields) => renderSingleRequirement(requirement, index, fields, identifier, nesting))
        }
        {error && <li className="error">{error}</li>}
        <div>
            <button type="button" onClick={() => fields.push()}>Add Requirement</button>
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
                <div className="col-md-1">
                    <button
                        type="button" className="btn btn-danger glyphicon glyphicon-trash"
                        title="Remove Requirement Group"
                        onClick={() => fields.remove(index)}
                    />
                </div>
            </div>
            <Field
                name={`${reqGroup}.name`}
                type="text"
                component={renderField}
                label="Group Name"
                className="text-left"
            />
            <FieldArray name={`${reqGroup}.requirements`} component={renderRequirements} identifier={newIdentifier}
                        nesting={nesting + 1}/>
            <FieldArray name={`${reqGroup}.requirementGroups`} component={renderRequirementGroups}
                        identifier={newIdentifier} nesting={nesting + 1}/>
        </div>
    )
};

const renderRequirementGroups = ({fields, meta: {error, submitFailed}, identifier, nesting}) => (
    <div className="row">
        <div className={`col-md-11 col-md-offset-1`}>
            <div className="requirement-groups list-group text-left">
                {
                    fields.map((reqGroup, index, fields) => (renderSingleRequirementGroup(reqGroup, index, fields, identifier, nesting)))
                }
                <div>
                    <button type="button" onClick={() => fields.push({})}>Add Requirement Group</button>
                    {submitFailed && error && <span>{error}</span>}
                </div>
            </div>
        </div>
    </div>
);

const Criterion = () => {
    return (
        <div className="criterion col-md-12 bg-info">
            <div className="row">
                <div className="col-md-2 text-left">
                    <Field
                        name="name"
                        type="text"
                        component={renderField}
                        label="Criterion Name"
                    />
                </div>
                <div className="col-md-11 col-md-offset-1">
                    <FieldArray name="requirementGroups" component={renderRequirementGroups} identifier="" nesting={1}/>
                </div>
            </div>
        </div>
    )
};

export default Criterion;
