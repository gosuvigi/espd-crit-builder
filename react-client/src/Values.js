/**
 * Created by vigi on 6/24/2017 1:40 PM.
 */
import React from 'react';
import { values as valuesDecorator } from 'redux-form';
import JsonView from 'react-pretty-json';

const Values = ({ form }) => {
    const decorator = valuesDecorator({ form });
    const component = ({ values = {} }) =>
        (
            <div>
                <h2>Values</h2>
                <JsonView id="json-pretty" json={values}></JsonView>
            </div>
        );
    const Decorated = decorator(component);
    return <Decorated/>
};

export default Values;