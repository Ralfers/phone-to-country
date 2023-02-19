import React from 'react'

import { Question } from 'react-bootstrap-icons'

const containerStyle = {
  height: '35px',
  width: '35px',
  backgroundColor: 'whitesmoke',
  display: 'flex',
  justifyContent: 'center',
  alignItems: 'center',
  borderRadius: '5px',
}

const UnknownCountryIcon = props => {
  const { style, title } = props

  return (
    <div style={{...containerStyle, ...style}} title={title}>
      <Question size={25} />
    </div>
  )
}

export default UnknownCountryIcon
