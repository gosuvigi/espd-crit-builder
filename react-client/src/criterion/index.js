/**
 * Created by vigi on 5/13/2017 4:38 PM.
 */
import {post} from '../restClient';
import {saveAs} from 'file-saver';

export const types = {
    SUBMIT_CRITERION_DEFINITION: 'SUBMIT_CRITERION_DEFINITION',
    DOWNLOAD_FILE: 'DOWNLOAD_FILE',
    CHANGE_RESPONSE_TYPE: 'CHANGE_RESPONSE_TYPE',
    LOAD_PASTED_JSON: 'LOAD_PASTED_JSON'
};

export const actions = {
    submitCriteria: (criterion) => {
        return (dispatch) => {
            post('/api/criteria', criterion)
                .then(response => {
                    if (response.ok) {
                        return response;
                    }
                    const error = new Error(`HTTP Error ${response.statusText}`);
                    error.status = response.statusText;
                    error.response = response;
                    error.id = new Date().getTime();
                    throw new Error({message: 'Error', _error: error});
                }).then(response => {
                return response.text();
            }).then((fileContent) => {
                dispatch(actions.downloadFile(fileContent));
            });
        };
    },
    downloadFile: (fileContent) => ({
        type: types.DOWNLOAD_FILE,
        payload: fileContent
    }),
    loadPastedJson: (json) => ({
        type: types.LOAD_PASTED_JSON,
        payload: json
    })
};

function stringToArrayBuffer(s) {
    const buf = new ArrayBuffer(s.length);
    const view = new Uint8Array(buf);
    for (let i = 0; i !== s.length; i++) {
        view[i] = s.charCodeAt(i) & 0xFF;
    }
    return buf;
}

export function reducer(state = {}, action = {}) {
    switch (action.type) {
        case types.DOWNLOAD_FILE:
            let data = stringToArrayBuffer(action.payload);
            saveAs(new Blob([data], {type: "application/xml"}), "espd-response.xml");
            return state;
        case types.CHANGE_RESPONSE_TYPE:
            return state;
        case types.LOAD_PASTED_JSON:
            if (!action.payload) {
                return {...state, parseException: null};
            }
            try {
                const parsed = JSON.parse(action.payload);
                return {...state, pastedJson: parsed, parseException: null};
            } catch (e) {
                return {...state, parseException: e};
            }
        default:
            return state;
    }
}
