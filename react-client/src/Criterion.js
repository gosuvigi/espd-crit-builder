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
        <li key={index} className="requirement list-group-item list-group-item-success">
            <button
                type="button" className="btn btn-danger glyphicon glyphicon-trash"
                title="Remove Requirement"
                onClick={() => fields.remove(index)}
            />
            <Field
                name={`${requirement}.name`}
                type="text"
                component={renderField}
                label={`Requirement #${newIdentifier} nesting ${nesting}`}
            />
        </li>
    )
};

const renderRequirements = ({fields, meta: {error}, identifier, nesting}) => (
    <ul className="requirements list-group">
        {
            fields.map((requirement, index, fields) => renderSingleRequirement(requirement, index, fields, identifier, nesting))
        }
        {error && <li className="error">{error}</li>}
        <li>
            <button type="button" onClick={() => fields.push()}>Add Requirement</button>
        </li>
    </ul>
);

const renderSingleRequirementGroup = (reqGroup, index, fields, identifier, nesting) => {
    const newIdentifier = !identifier ? (index + 1) : identifier + "." + (index + 1);
    return (
        <li key={index} className="requirement-group list-group-item list-group-item-warning">
            <button
                type="button" className="btn btn-danger glyphicon glyphicon-trash"
                title="Remove Requirement Group"
                onClick={() => fields.remove(index)}
            />
            <h4>Requirement group #{newIdentifier} nesting {nesting}</h4>
            <Field
                name={`${reqGroup}.name`}
                type="text"
                component={renderField}
                label="Group Name"
            />
            <FieldArray name={`${reqGroup}.requirements`} component={renderRequirements} identifier={newIdentifier}
                        nesting={nesting + 1}/>
            <FieldArray name={`${reqGroup}.requirementGroups`} component={renderRequirementGroups}
                        identifier={newIdentifier} nesting={nesting + 1}/>
        </li>
    )
};

const renderRequirementGroups = ({fields, meta: {error, submitFailed}, identifier, nesting}) => (
    <ul className="requirement-groups list-group">
        {
            fields.map((reqGroup, index, fields) => (renderSingleRequirementGroup(reqGroup, index, fields, identifier, nesting)))
        }
        <li>
            <button type="button" onClick={() => fields.push({})}>Add Requirement Group</button>
            {submitFailed && error && <span>{error}</span>}
        </li>
    </ul>
);

const Criterion = () => {
    return (
        <div className="criterion col-md-12">
            <div className="row pull-left">
                <div className="col-md-12">
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
