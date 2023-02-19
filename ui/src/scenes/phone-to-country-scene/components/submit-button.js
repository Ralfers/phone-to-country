import React from 'react'

import Button from 'react-bootstrap/Button'

const buttonStyle = {
  marginLeft: '16px',
}

const SubmitButton = props => {
  const { onClick, disabled } = props

  return (
    <Button
      variant='dark'
      size='sm'
      style={buttonStyle}
      onClick={onClick}
      disabled={disabled}
    >
      Get countries
    </Button>
  )
}

export default SubmitButton
