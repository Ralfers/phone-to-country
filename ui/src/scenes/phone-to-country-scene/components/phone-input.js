import React, { useState } from 'react'

import Form from 'react-bootstrap/Form'

const containerStyle = {
  display: 'flex',
  direction: 'row',
  alignItems: 'center',
  height: '35px',
}

const inputStyle = {
  width: '140px',
  height: '100%',
  display: 'inline',
  paddingLeft: '3px',
}

const alertStyle = {
  width: '200px',
}

const PhoneInput = props => {
  const { value, setValue, setErrorMessage } = props

  const isValidPhoneNumber = phoneNumber => {
    return phoneNumber && phoneNumber.length >= 7 && phoneNumber.length <= 15 && /^\d*$/.test(phoneNumber)
  }

  const isAllowedInput = phoneNumber => {
    return !phoneNumber || phoneNumber.length <= 15 && /^\d*$/.test(phoneNumber)
  }

  const onChange = event => {
    const newValue = event.target.value

    if (!isAllowedInput(newValue)) return

    setValue(newValue)

    if (!isValidPhoneNumber(newValue)) {
      setErrorMessage('Phone number must only contain 7-15 digits')
    } else {
      setErrorMessage('')
    }
  }

  return (
    <div style={containerStyle}>
      +
      <Form.Control
        type='text'
        size='sm'
        style={inputStyle}
        value={value || ''}
        onChange={onChange}
      />
    </div>
  )
}

export default PhoneInput
