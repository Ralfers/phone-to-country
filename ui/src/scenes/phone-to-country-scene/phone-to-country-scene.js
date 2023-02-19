import React, { useState } from 'react'

import Form from 'react-bootstrap/Form'

import PhoneInput from './components/phone-input'
import ErrorLabel from './components/error-label'
import SubmitButton from './components/submit-button'
import PhoneCountries from './components/phone-countries'

const containerStyle = {
  padding: '16px',
}

const contentContainerStyle = {
  marginLeft: '16px',
}

const inputContainerStyle = {
  display: 'flex',
  direction: 'row',
  alignItems: 'center',
}

const titleStyle = {
  marginBottom: '32px',
  fontWeight: '600',
  fontSize: '20px',
}

const PhoneToCountryScene = () => {
  const [phoneNumber, setPhoneNumber] = useState('')
  const [errorMessage, setErrorMessage] = useState('')
  const [countryCodes, setCountryCodes] = useState([])

  const getCountryCodes = () => {
    fetch('/country-codes?' + new URLSearchParams({
        phoneNumber: `+${phoneNumber}`,
    }))
    .then(response => {
      if (response.ok) {
        return response.json()
      }
      throw new Error('Error while fetching country codes')
    })
    .then(data => {
      setCountryCodes(data.countryCodes)
    })
    .catch(error => {
      setErrorMessage('Unknown phone number')
      setCountryCodes([])
    })
  }

  return (
    <div style={containerStyle}>
      <Form.Label style={titleStyle}>Input your phone number:</Form.Label>
      <div style={contentContainerStyle}>
        <div style={inputContainerStyle}>
          <PhoneInput value={phoneNumber} setValue={setPhoneNumber} setErrorMessage={setErrorMessage} />
          <SubmitButton disabled={!phoneNumber || errorMessage} onClick={getCountryCodes} />
        </div>
        {errorMessage && <ErrorLabel>{errorMessage}</ErrorLabel>}
        {countryCodes.length > 0 && <PhoneCountries countryCodes={countryCodes} />}
      </div>
    </div>
  )
}

export default PhoneToCountryScene
