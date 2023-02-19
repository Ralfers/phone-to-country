import React from 'react'

import Form from 'react-bootstrap/Form'
import Flags from 'country-flag-icons/react/3x2'

import UnknownCountryIcon from './unknown-country-icon'

const containerStyle = {
  marginTop: '24px',
  height: '35px',
  display: 'flex',
  direction: 'row',
  alignItems: 'center',
}

const labelStyle = {
  marginBottom: '0px',
  marginRight: '16px',
}

const flagStyle = {
  width: '35px',
  marginRight: '8px',
}

const unknownFlagStyle = {
  marginRight: '8px',
}

const PhoneCountries = props => {
  const { countryCodes } = props

  const mapCodesToIcons = () => {
    return countryCodes.map(countryCode => {
      const Flag = Flags[countryCode]
      return !Flag ? <Flag style={flagStyle} key={countryCode} title={countryCode} /> : <UnknownCountryIcon style={unknownFlagStyle} key={countryCode} title={countryCode} />
    })
  }

   const flagIcons = mapCodesToIcons()

  return (
    <div style={containerStyle}>
      <Form.Label style={labelStyle}>The phone number is from the following countries:</Form.Label>
      {mapCodesToIcons()}
    </div>
  )
}

export default PhoneCountries
