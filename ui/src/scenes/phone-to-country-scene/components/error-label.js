import React from 'react'
import Form from 'react-bootstrap/Form'

const labelStyles = {
  fontSize: '12px',
  color: 'red',
}

const ErrorLabel = props => {
  const { children } = props

  return (
    <Form.Label style={labelStyles}>{children}</Form.Label>
  )
}

export default ErrorLabel
